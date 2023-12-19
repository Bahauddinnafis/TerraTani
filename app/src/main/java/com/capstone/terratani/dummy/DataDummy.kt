package com.capstone.terratani.dummy

import com.capstone.terratani.R
import com.capstone.terratani.model.Category
import com.capstone.terratani.model.Features
import com.capstone.terratani.model.ImageShop
import com.capstone.terratani.model.Plant
import com.capstone.terratani.model.Treatment

object DataDummy {
    val allPlants = listOf(
        Plant(
            "Pohon Beringin",
            "Lorem Ipsum",
            R.drawable.pohon_beringin
        ),
        Plant(
            "Pohon Mangga",
            "Lorem Ipsum",
            R.drawable.pohon_mangga
        ),
        Plant(
            "Pohon Sawit",
            "Lorem Ipsum",
            R.drawable.pohon_sawit
        ),
        Plant(
            "Janda Bolong",
            "Lorem Ipsum",
            R.drawable.janda_bolong
        ),
        Plant(
            "Lidah Buaya",
            "Lorem Ipsum",
            R.drawable.lidah_buaya
        ),
        Plant(
            "Lavender",
            "Lorem Ipsum",
            R.drawable.lavender
        )
    )

    val bigPlants = listOf(
        Plant(
            "Pohon Beringin",
            "Lorem Ipsum",
            R.drawable.pohon_beringin
        ),
        Plant(
            "Pohon Mangga",
            "Lorem Ipsum",
            R.drawable.pohon_mangga
        ),
        Plant(
            "Pohon Sawit",
            "Lorem Ipsum",
            R.drawable.pohon_sawit
        )
    )

    val indoor = listOf(
        Plant(
            "Janda Bolong",
            "Lorem Ipsum",
            R.drawable.janda_bolong
        ),
        Plant(
            "Lidah Buaya",
            "Lorem Ipsum",
            R.drawable.lidah_buaya
        ),
        Plant(
            "Lavender",
            "Lorem Ipsum",
            R.drawable.lavender
        )
    )

    val outdoor = listOf(
        Plant(
            "Pohon Beringin",
            "Lorem Ipsum",
            R.drawable.pohon_beringin
        ),
        Plant(
            "Pohon Mangga",
            "Lorem Ipsum",
            R.drawable.pohon_mangga
        ),
        Plant(
            "Pohon Sawit",
            "Lorem Ipsum",
            R.drawable.pohon_sawit
        )
    )

    val treatment = listOf(
        Treatment(
            "Cara Agar Tanaman Subur"
        ),
        Treatment(
            "Merawat Tanaman Sulit?"
        ),
        Treatment(
            "Pupuk Bagus Untuk Tanah"
        )
    )

    val features = listOf(
        Features(
            "Scan Tanah"
        ),
        Features(
            "Sewa Tanah"
        ),
        Features(
            "Beli Tanah"
        ),
        Features(
            "Jual Tanah"
        )
    )

    val category = listOf(
        Category(
            "Bibit Unggul Pohon Cabe",
            "Rp2.000.500",
            "Kota Bandung",
            R.drawable.pohon_cabe
        ),
        Category(
            "Bibit Unggul Pohon Apel",
            "Rp3.000.000",
            "Kota Bogor",
            R.drawable.buah_apel
        ),
        Category(
            "Bibit Unggul Pohon Nangka",
            "Rp4.000.000",
            "Kota Jakarta",
            R.drawable.buah_nangka
        )
    )

    val imageShop = listOf(
        ImageShop(
            R.drawable.rice_fields
        ),
        ImageShop(
            R.drawable.greenhouse
        ),
        ImageShop(
            R.drawable.gardener
        ),
        ImageShop(
            R.drawable.nature
        ),
        ImageShop(
            R.drawable.farmer
        ),
        ImageShop(
            R.drawable.pople_planting
        )
    )
}