package com.ort.tourismapp.entities

class GuideRepository () {
    var guides : MutableList<Guide> = mutableListOf()

    init {
        guides.add(Guide(1, "Tamara", "Aida", "t@Aida", "11-1111-1111", "CABA", "Buenos Aires", "Argentina", "Esta es mi biografia", "URL Foto", "7,5" ))
        guides.add(Guide(1, "Mariana", "Baena", "m@Baena", "11-2222-2222", "CABA", "Buenos Aires", "Argentina", "Esta es mi biografia", "URL Foto", "7,5"))
        guides.add(Guide(1, "Juan Cruz", "Gaglio", "jc@Gaglio", "11-3333-3333", "CABA", "Buenos Aires", "Argentina", "Esta es mi biografia", "URL Foto", "7,5"))
        guides.add(Guide(1, "Esteban", "Monzón", "e@monzon", "11-4444-4444", "CABA", "Buenos Aires", "Argentina", "Esta es mi biografia", "URL Foto", "7,5"))
        guides.add(Guide(1, "Celina", "Rinaldi", "c@Rinaldi", "11-5555-5555", "CABA", "Buenos Aires", "Argentina", "Esta es mi biografia", "URL Foto", "7,5"))
        guides.add(Guide(1, "Lucio", "Lucio", "l@Lucio", "11-5555-5555", "CABA", "Buenos Aires", "Argentina", "Esta es mi biografia", "URL Foto", "7,5"))
    }
}