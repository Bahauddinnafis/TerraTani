// handler.js
const { Storage } = require('@google-cloud/storage');

const storage = new Storage({
  projectId: 'terratanipart1',
  keyFilename: 'key.json',
});
const bucketName = 'soil-image-terratani';

const uploadToStorage = (file) => {
  const bucket = storage.bucket(bucketName);
  const fileName = `${Date.now()}_${file.originalname}`;
  const fileUpload = bucket.file(fileName);

  const stream = fileUpload.createWriteStream({
    metadata: {
      contentType: file.mimetype,
    },
  });

  return new Promise((resolve, reject) => {
    stream.on('error', reject);
    stream.on('finish', () => {
      const publicUrl = `https://storage.googleapis.com/${bucketName}/${fileName}`;
      resolve(publicUrl);
    });

    stream.end(file.buffer);
  });
};

module.exports = { uploadToStorage };
