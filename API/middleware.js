// middleware.js
const multer = require('multer');
const { uploadToStorage } = require('./handler');

const storage = multer.memoryStorage();
const upload = multer({ storage: storage });

const handleFileUpload = async (req, res, next) => {
  try {
    const file = req.file;
    const imageUrl = await uploadToStorage(file);
    req.imageUrl = imageUrl;
    next();
  } catch (error) {
    console.error('Error handling file upload:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
};


const jwt = require('jsonwebtoken');

const authenticateToken = (req, res, next) => {
  const token = req.headers['authorization'];

  if (!token) {
    return res.status(401).json({ error: 'Unauthorized' });
  }

  jwt.verify(token, 'your-secret-key', (err, user) => {
    if (err) {
      if (err.name === 'TokenExpiredError') {
        return res.status(401).json({ error: 'Token has expired' });
      } else {
        console.error('Error verifying token:', err);
        return res.status(403).json({ error: 'Forbidden' });
      }
    }
  
    req.user = user;
    next();
  });
};
module.exports = { authenticateToken, upload, handleFileUpload };