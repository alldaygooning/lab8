package module;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import application.*;
import attribute.*;
import collection.*;
import item.*;

public class DatabaseModule {
	private static final String host = "localhost";
	private static final String username = "postgres";
	private static final String password = System.getenv("postgresPassword");
	private static final String url = String.format("jdbc:postgresql://%s:5432/studs", host);
	
	private static final HikariConfig config = new HikariConfig();
	private static HikariDataSource dataSource;
	
	public static void turnOn() {
		
		config.setJdbcUrl(url);
		config.setUsername(username);
		config.setPassword(password);
		config.setMinimumIdle(10);
		config.setMaximumPoolSize(10);
		
		dataSource = new HikariDataSource(config);
		
		Application.logger.info("Database connection established.");
	}
	
	
	public static List<MusicBand> fetchMusicBands() throws SQLException{
		List<MusicBand> musicBands = new ArrayList<MusicBand>();
		Connection connection = dataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet bandFields = statement.executeQuery("select * from musicband");
		while(bandFields.next()) {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from person where id = ?");
			preparedStatement.setInt(1, bandFields.getInt("frontManID"));
			ResultSet personFields = preparedStatement.executeQuery();
			personFields.next();
			Person person = new Person(new PersonBuilder()
					.name(personFields.getString("name"))
					.height((personFields.getLong("height")!=0)?personFields.getLong("height"):null)
					.weight(personFields.getInt("weight"))
					.hairColor(Color.valueOf(personFields.getString("hairColor")))
					.nationality(Country.valueOfNullable(personFields.getString("nationality")))
					);
			preparedStatement.close();
			personFields.close();
			
			MusicBand band = new MusicBand(new MusicBandBuilder()
					.id(bandFields.getInt("id"))
					.name(bandFields.getString("name"))
					.coordinates(new Coordinates(bandFields.getDouble("x"), bandFields.getDouble("y")))
					.creationDate(LocalDate.parse(bandFields.getDate("creationdate").toString()))
					.numberOfParticipants(bandFields.getLong("numberOfParticipants"))
					.genre(MusicGenre.valueOfNullable(bandFields.getString("genre")))
					.frontMan(person)
					.owner(bandFields.getString("owner"))
					);
			
			musicBands.add(band);
		}
		
		connection.close();
		statement.close();
		bandFields.close();
		return musicBands;
	}

	public static List<User> fetchUsers() throws SQLException{
		List<User> users = new ArrayList<User>();
		Connection connection = dataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("select * from profile");
		
		while(result.next()) {
			users.add(new User(result.getString("username"), result.getString("password"), result.getString("level")));
		}
		
		result.close();
		statement.close();
		connection.close();
		Application.logger.info("Profiles loaded.");
		return users;
	}
	
	synchronized public static void insertUser(User user) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement("insert into profile(username, password, level) values(?, ?, ?)")){
			
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setObject(3, user.getAccess(), Types.OTHER);
			
			preparedStatement.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateUserPassword(User user) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement("update profile set password = ? where username = ?")){
			preparedStatement.setString(1, user.getPassword());
			preparedStatement.setString(2, user.getName());
			
			preparedStatement.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertMusicBand(MusicBand band) { 
		try(Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatementPerson = connection.prepareStatement("insert into person(name, height, weight, haircolor, nationality) values(?, ?, ?, ?, ?) returning id");
				PreparedStatement preparedStatementBand = 
						connection.prepareStatement("insert into musicband(name, x, y, creationdate, numberofparticipants, genre, frontmanid, owner) values(?, ?, ?, ?, ?, ?, ?, ?) returning id");){
			Person person = band.getFrontMan();
			preparedStatementPerson.setString(1, person.getName());
			if(person.getHeight() == null) {
				preparedStatementPerson.setNull(2, Types.BIGINT);
			}else {
				preparedStatementPerson.setLong(2, person.getHeight());
			}
			preparedStatementPerson.setInt(3, person.getWeight());
			preparedStatementPerson.setObject(4, person.getHairColor(), Types.OTHER);
			if(person.getCountry() == null) {
				preparedStatementPerson.setNull(5, Types.OTHER);
			}else {
				preparedStatementPerson.setObject(5, person.getCountry(), Types.OTHER);
			}
			ResultSet resultPersonID = preparedStatementPerson.executeQuery();
			resultPersonID.next();
			
			preparedStatementBand.setString(1, band.getName());
			preparedStatementBand.setDouble(2, band.getX());
			preparedStatementBand.setDouble(3, band.getY());
			preparedStatementBand.setDate(4, Date.valueOf(band.getCreationDate()));
			preparedStatementBand.setLong(5, band.getNumberOfParticipants());
			if(band.getGenre() == null) {
				preparedStatementBand.setNull(6, Types.OTHER);
			}else {
				preparedStatementBand.setObject(6, band.getGenre(), Types.OTHER);
			}
			preparedStatementBand.setInt(7, resultPersonID.getInt("id"));
			preparedStatementBand.setString(8, band.getOwner());
			
			ResultSet resultBandID = preparedStatementBand.executeQuery();
			resultBandID.next();
			band.setId(resultBandID.getInt("id"));
			
			resultBandID.close();
			resultPersonID.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateMusicBand(MusicBand band) {
		try(Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatementPerson = connection.prepareStatement("update person set name = ?, height = ?, weight = ?,"
						+ " haircolor = ?, nationality = ? where id = ?");
				PreparedStatement preparedStatementBand = connection.prepareStatement("update musicband set name = ?, x = ?, y = ?, creationdate = ?, numberofparticipants = ?,"
								+ " genre = ? where id = ?");
				Statement statement = connection.createStatement();){
			String query = String.format("select frontmanid from musicband where id = %s", band.getId());
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			int frontmanid = resultSet.getInt("frontmanid");
			
			Person person = band.getFrontMan();
			
			preparedStatementPerson.setString(1, person.getName());
			if(person.getHeight() == null) {
				preparedStatementPerson.setNull(2, Types.BIGINT);
			}else {
				preparedStatementPerson.setLong(2, person.getHeight());
			}
			preparedStatementPerson.setInt(3, person.getWeight());
			preparedStatementPerson.setObject(4, person.getHairColor(), Types.OTHER);
			if(person.getCountry() == null) {
				preparedStatementPerson.setNull(5, Types.OTHER);
			}else {
				preparedStatementPerson.setObject(5, person.getCountry(), Types.OTHER);
			}
			preparedStatementPerson.setInt(6, frontmanid);
			preparedStatementPerson.executeUpdate();
			
			preparedStatementBand.setString(1, band.getName());
			preparedStatementBand.setDouble(2, band.getX());
			preparedStatementBand.setDouble(3, band.getY());
			preparedStatementBand.setDate(4, Date.valueOf(band.getCreationDate()));
			preparedStatementBand.setLong(5, band.getNumberOfParticipants());
			if(band.getGenre() == null) {
				preparedStatementBand.setNull(6, Types.OTHER);
			}else {
				preparedStatementBand.setObject(6, band.getGenre(), Types.OTHER);
			}
			preparedStatementBand.setInt(7, band.getId());
			
			preparedStatementBand.executeUpdate();
			
			resultSet.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeById(int id) {
		try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement();){
			String query = String.format("delete from musicband where id = %s",id);
			statement.executeUpdate(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void shutDown() {
		dataSource.close();
	}
}
