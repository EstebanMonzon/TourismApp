package com.ort.tourismapp.entities

class ActivityRepository() {
    var activities : MutableList<Activity> = mutableListOf()

    init {
        activities.add(Activity(1, "Usina del Arte", "CABA", "Buenos Aires", "Argentina", "Hola soy la actividad Usina del Arte", "URL Foto", "7"))
        activities.add(Activity(2, "Estadio de Boca Jrs", "CABA", "Buenos Aires", "Argentina", "Hola soy la actividad Estadio de Boca Jrs", "URL Foto", "7"))
        activities.add(Activity(3, "Museo de bellas Artes", "CABA", "Buenos Aires", "Argentina", "Hola soy la actividad Museo de bellas Artes", "URL Foto", "7"))
        activities.add(Activity(4, "Caminito", "CABA", "Buenos Aires", "Argentina", "Hola soy la actividad Caminito", "URL Foto", "7"))
    }
}