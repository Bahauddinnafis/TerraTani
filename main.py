# Import library yang dibutuhkan
from flask import Flask, request, jsonify
import numpy as np
from tensorflow.keras.models import load_model
from PIL import Image
import io

# Inisialisasi aplikasi Flask
app = Flask(__name__)

# Load model machine learning
model = load_model('Rekomendasi_tanaman.h5')

# Endpoint untuk merekomendasikan tanaman
@app.route('/rekomendasi_tanaman', methods=['POST'])
def rekomendasi_tanaman():
    try:
        # Ambil data JSON dari request
        data = request.get_json()

        # Lakukan pre-processing sesuai dengan kebutuhan model Anda
        # Misalnya, konversi data input menjadi array numpy
        input_data = np.array(data['input_features']).reshape(1, -1)

        # Lakukan prediksi menggunakan model Rekomendasi_tanaman
        prediction = rekomendasi_tanaman_model.predict(input_data)

        # Proses hasil prediksi sesuai dengan kebutuhan
        # Misalnya, ambil hasil prediksi sebagai rekomendasi tanaman
        recommended_plant = int(prediction[0])

        # Kembalikan hasil rekomendasi tanaman dalam bentuk JSON
        result = {'recommended_plant': recommended_plant}

        return jsonify(result)

    except Exception as e:
        return jsonify({'error': str(e)})

# Menjalankan aplikasi Flask
if __name__ == '__main__':
    app.run(debug=True)
