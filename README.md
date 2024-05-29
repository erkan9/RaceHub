# RaceHub Documentation

## Project Overview

RaceHub is a web application designed as a centralized platform for kart racing enthusiasts. It provides a comprehensive set of features for racers to track and analyze their go-karting experience. From lap times to total driving time and kilometers driven, RaceHub offers valuable insights into a racer's performance. Additionally, racers can view detailed information about their laps, races, and rankings across various karting circuits in Bulgaria.

## Installation and Usage

### Prerequisites
- Java JDK 11 or higher
- Maven
- PostgreSQL

### Installation Steps
1. Clone the Repository:
   ```bash
   git clone https://github.com/erkamber/RaceHub.git
   ```

2. Navigate to the Project Directory:
   ```bash
   cd RaceHub
   ```

3. Build the Project:
   ```bash
   mvn clean install
   ```

4. Set Up PostgreSQL Database:
   - Create a database named `racehub`.
   - Configure the database connection settings in `application.properties`.

5. Set Up Environment Variables:
   - Set the following environment variables in your system:
     ```properties
     ENVIRONMENT_LOCAL=local
     NEWSLINKER_EMAIL_HOST=<your_email_host>
     NEWSLINKER_EMAIL_PORT=<your_email_port>
     NEWSLINKER_EMAIL=<your_email_address>
     NEWSLINKER_EMAIL_PASS=<your_email_password>
     ```

6. Run the Application:
   ```bash
   java -jar -Dserver.port=3000 target/RaceHub-3.0.0.jar
   ```

7. Access the Website:
   Navigate to `http://localhost:3000` in your web browser.

### Usage
- **Register:** Sign up with your email and password.
- **Log in:** Log in with your registered credentials.
- **Explore Races:** View available races and their details.
- **Manage Racers:** Add, update, or delete racer information.
- **View Rankings:** See the rankings based on lap times and sessions.

## Business Logic

### Profiles
The application supports two profiles: `local` and `dev`.

### Database Configuration
The application uses PostgreSQL as the database. The connection settings are configured in `application.properties`.

### Logging
Logging settings are configured to output logs to a file located in the `logs` directory. Log levels for different packages and classes are specified to control the verbosity of logs.

### Email Configuration
Email settings are configured to send emails for notifications and communication features. SMTP host, port, username, and password are provided as system environment variables.

### Other Settings
Other settings such as server port and ANSI color support are configured in `application.properties`.

## Contributing
Contributions are welcome! If you find any issues or have suggestions for improvements, feel free to open an issue or submit a pull request.
