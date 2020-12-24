package model

class Card(var cityName: String, var mallName: String) {
    override fun toString(): String {
        return "你到了 $cityName 的 $mallName"
    }

}