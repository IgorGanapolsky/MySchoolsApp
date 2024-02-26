package com.cnh.samples.apps.schools.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * [MySchool] represents when a user adds a [School] to their My Schools page, with useful metadata.
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
 */
@Entity(
    tableName = "my_schools",
    foreignKeys = [
        ForeignKey(entity = School::class, parentColumns = ["dbn"], childColumns = ["dbn"]),
    ],
    indices = [Index("dbn")],
)
data class MySchool(
    @ColumnInfo(name = "dbn")
    @SerializedName("dbn")
    val schoolDbn: String = "",

    @ColumnInfo(name = "school_name")
    @SerializedName("school_name")
    val schoolName: String? = "",

    @ColumnInfo(name = "boro")
    @SerializedName("boro")
    val schoolBorough: String = "",

    @ColumnInfo(name = "overview_paragraph")
    @SerializedName("overview_paragraph")
    val overviewParagraph: String? = "",

    @ColumnInfo(name = "academicOpportunities1")
    @SerializedName("academicopportunities1")
    val academicOpportunities1: String? = "",

    @ColumnInfo(name = "academicOpportunities2")
    @SerializedName("academicopportunities2")
    val academicOpportunities2: String? = "",

    @ColumnInfo(name = "academicOpportunities3")
    @SerializedName("academicopportunities3")
    val academicOpportunities3: String? = "",

    @ColumnInfo(name = "academicOpportunities4")
    @SerializedName("academicopportunities4")
    val academicOpportunities4: String? = "",

    @ColumnInfo(name = "academicOpportunities5")
    @SerializedName("academicopportunities5")
    val academicOpportunities5: String? = "",

    @ColumnInfo(name = "location") val location: String = "",
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var schoolId: Long = 0
}
