// auth.js
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

const generateToken = (user) => {
  return jwt.sign({ id: user.id, username: user.username, email: user.email }, 'your-secret-key', { expiresIn: '1h' });
};

const comparePasswords = async (password, hashedPassword) => {
  return await bcrypt.compare(password, hashedPassword);
};

module.exports = { generateToken, comparePasswords };
