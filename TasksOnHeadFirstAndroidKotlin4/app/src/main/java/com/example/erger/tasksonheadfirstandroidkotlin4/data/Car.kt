package com.example.erger.tasksonheadfirstandroidkotlin4.data

class Car(var name: String, var description: String) {

    override fun toString(): String {
        return this.name
    }

    companion object {
        val cars = arrayOf(Car("Audi A4",
                "Middle class sedan"), Car("BMW 7 series",
                "Premium class sedan"), Car("VAZ 2101",
                "Literally the best car humanity ever created"))
    }
}