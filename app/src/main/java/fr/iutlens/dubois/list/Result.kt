package fr.iutlens.dubois.list

sealed class Result(open val description : String){
    data class Success(override val description: String) : Result(description)
    data class Error(override val description: String) : Result(description)
    data class Processing(override val description: String) : Result(description)
}
