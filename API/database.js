// database.js
const mysql = require('mysql');

const connection = mysql.createConnection({
    host: '34.128.116.201',
    user: 'root',
    password: 'terratanisql',
    database: 'server-databases',
  });
  
  connection.connect((err) => {
    if (err) {
      console.error('Error connecting to MySQL:', err);
      return;
    }
    console.log('Connected to MySQL database');
  
    // Create user table if not exists
    // const createUserTable = `
    //   CREATE TABLE IF NOT EXISTS users (
    //     id INT AUTO_INCREMENT PRIMARY KEY,
    //     username VARCHAR(255) UNIQUE NOT NULL,
    //     password VARCHAR(255) NOT NULL
    //   )
    // `;
    
    // connection.query(createUserTable, (error) => {
    //   if (error) {
    //     console.error('Error creating user table:', error);
    //   } else {
    //     console.log('User table created or already exists');
    //   }
    // });
  });
  
  module.exports = connection;
  