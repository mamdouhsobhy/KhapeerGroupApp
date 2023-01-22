package com.example.khapeergroup.auth.data.responseremote

data class ModelLoginResponseRemote(
    val AccountId: Int,
    val AccountRole: Any,
    val Activation: Boolean,
    val ArabicMessage: String,
    val Code: Int,
    val CurrentPage: Int,
    val EnglishMessage: String,
    val IsArabic: Any,
    val PageCount: Int,
    val Success: Boolean,
    val Token: String,
    val UserRole: Any,
    val UserType: Int,
    val VisitStatus: Any,
    val user: User
)
data class User(
    val AmbulanceProfileId: Any,
    val AspNetUsersId: Int,
    val BirthDate: String,
    val ClassArabicName: String,
    val ClassEnglishName: String,
    val ClassId: Int,
    val Email: Any,
    val EmailConfirmed: Boolean,
    val FirstNameAr: String,
    val FirstNameEn: String,
    val Gender: Int,
    val HasInsurance: Boolean,
    val Id: Int,
    val LastNameAr: String,
    val LastNameEn: String,
    val LicenseNumber: Any,
    val MobileNumber: String,
    val PatientImage: String,
    val PhoneNumberConfirmed: Boolean,
    val Source: Any,
    val UserName: Any
)