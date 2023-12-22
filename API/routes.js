// routes.js
const express = require('express');
const router = express.Router();
const middleware = require('./middleware');
const database = require('./database');
const auth = require('./auth');
const bcrypt = require('bcrypt');
const { Storage } = require('@google-cloud/storage');
const multer = require('multer');

//REGISTRASI
router.get('/register', (_req, res) => {
  res.send('Hello, this is Terratani register API!');
});

router.post('/register', async (req, res) => {
  const { username, email, password } = req.body;
  const hashedPassword = await bcrypt.hash(password, 10);
  const createUserQuery = 'INSERT INTO users (username, email, password) VALUES (?, ?, ?)';
  const createUserValues = [username, email, hashedPassword];

  database.query(createUserQuery, createUserValues, (error, results) => {
    if (error) {
      console.error('Error registering user:', error);
      res.status(500).json({ error: 'Email is already in use' });
    } else {
      res.json({ message: 'User registered successfully' });
    }
  });
});

//LOGIN
router.get('/login', (_req, res) => {
  res.send('Hello, this is Terratani login API!');
});

router.post('/login', async (req, res) => {
  const { email, password } = req.body;
  const getUserQuery = 'SELECT * FROM users WHERE email = ?';
  const getUserValues = [email];

  database.query(getUserQuery, getUserValues, async (error, results) => {
    if (error) {
      console.error('Error retrieving user:', error);
      res.status(500).json({ error: 'Internal Server Error' });
    } else {
      if (results.length > 0) {
        const user = results[0];
        const isPasswordValid = await auth.comparePasswords(password, user.password);

        if (isPasswordValid) {
          const token = auth.generateToken(user);
          res.json({ message: 'User login successfully', token });
        } else {
          res.status(401).json({ error: 'Invalid credentials' });
        }
      } else {
        res.status(401).json({ error: 'User not found' });
      }
    }
  });
});

//UPLOAD FOTO TANAH
const storage = new Storage({
  keyFilename: 'key.json',
});

const bucketName = 'soil-image-terratani';
const bucket = storage.bucket(bucketName);

const multerStorage = multer.memoryStorage();
const upload = multer({ storage: multerStorage });

router.post('/upload-image', middleware.authenticateToken, upload.single('soilImage'), async (req, res) => {
  try {
    if (!req.file) {
      return res.status(400).json({ error: 'No file uploaded' });
    }

    const { originalname, buffer } = req.file;
    const userId = req.user.id;

    const fileName = `${userId}_${Date.now()}_${originalname}`;
    const file = bucket.file(fileName);

    const stream = file.createWriteStream({
      metadata: {
        contentType: req.file.mimetype,
      },
    });

    stream.on('error', (err) => {
      console.error('Error uploading to Google Cloud Storage:', err);
      return res.status(500).json({ error: 'Internal Server Error' });
    });

    stream.on('finish', async () => {
      const imageUrl = `https://storage.googleapis.com/${bucketName}/${fileName}`;
      return res.json({ message: 'Image uploaded successfully', imageUrl });
    });

    stream.end(buffer);
  } catch (error) {
    console.error('Error processing image upload:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

// PILIHAN REKOMENDASI
router.get('/recommendation', (_req, res) => {
  res.send ('Terratani Features: \n 1. Crop recommendation \n 2. Fertilizer Recommendations \n 3. Yield Prediction');
});

// INPUT DATA TANAH REKOMENDASI TANAMAN
router.post('/crop-recommendation', middleware.authenticateToken, async (req, res) => {
  const { nitrogen, phosporus, k, humidity, temperature, ph, rainfall } = req.body;
  const email = req.user.email;
  const username = req.user.username;

  const insertSoilDataQuery = 'INSERT INTO crop_recommendation (email, username, nitrogen, phosporus, k, humidity, temperature, ph, rainfall) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)';
  const insertSoilDataValues = [email, username, nitrogen, phosporus, k, humidity, temperature, ph, rainfall];

  database.query(insertSoilDataQuery, insertSoilDataValues, (error, results, fields) => {
    if (error) {
      console.error('Error inserting soil data:', error);
      return res.status(500).json({ error: 'Internal Server Error' });
    } else {
      return res.json({ message: 'Soil data uploaded successfully' });
    }
  });
});

//INPUT DATA REKOMENDASI PUPUK
router.post('/fertilizer-recommendation', middleware.authenticateToken, async (req, res) => {
  const { nitrogen, phosporus, k, humidity, temperature, moisture, crop_type } = req.body;
  const email = req.user.email;
  const username = req.user.username;

  const insertSoilDataQuery = 'INSERT INTO fertilizer_recommendation (email, username, nitrogen, phosporus, k, humidity, temperature, moisture, crop_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)';
  const insertSoilDataValues = [email, username, nitrogen, phosporus, k, humidity, temperature, moisture, crop_type];

  database.query(insertSoilDataQuery, insertSoilDataValues, (error, results, fields) => {
    if (error) {
      console.error('Error inserting soil data:', error);
      return res.status(500).json({ error: 'Internal Server Error' });
    } else {
      return res.json({ message: 'Soil data uploaded successfully' });
    }
  });
});

//INPUT DATA PREDIKSI PANEN
router.post('/yield-prediction', middleware.authenticateToken, async (req, res) => {
  const { area, crop_type, year, avg_rainfall, pesticides_tones, avg_temp } = req.body;
  const email = req.user.email;
  const username = req.user.username;

  const insertSoilDataQuery = 'INSERT INTO yield_prediction (email, username, area, crop_type, year, avg_rainfall, pesticides_tones, avg_temp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)';
  const insertSoilDataValues = [email, username, area, crop_type, year, avg_rainfall, pesticides_tones, avg_temp];

  database.query(insertSoilDataQuery, insertSoilDataValues, (error, results, fields) => {
    if (error) {
      console.error('Error inserting soil data:', error);
      return res.status(500).json({ error: 'Internal Server Error' });
    } else {
      return res.json({ message: 'Soil data uploaded successfully' });
    }
  });
});

module.exports = router;