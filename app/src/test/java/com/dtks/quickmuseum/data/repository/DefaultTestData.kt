package com.dtks.quickmuseum.data.repository

import com.dtks.quickmuseum.data.model.ArtCreator
import com.dtks.quickmuseum.data.model.ArtDetailsDao
import com.dtks.quickmuseum.data.model.ArtDetailsResponse
import com.dtks.quickmuseum.data.model.ArtObjectDao
import com.dtks.quickmuseum.data.model.CollectionResponse
import com.dtks.quickmuseum.data.model.Dating
import com.dtks.quickmuseum.data.model.WebImage
import com.dtks.quickmuseum.ui.details.ArtDetails
import com.dtks.quickmuseum.ui.details.ArtDetailsImage
import com.dtks.quickmuseum.ui.overview.ArtObjectListItem


val defaultArtId = "art1"
val defaultObjectNumber = "artObject1"
val defaultTitle = "Eternal sunshine of the spotless mind"
val defaultMaterials = listOf("aluminium", "copper")
val defaultTechniques = listOf("acting")
val defaultDatingString = "2004"
val defaultImageUrl = "imageUrl.com/cool"
val defaultCreator = "Charlie Kaufman"
val defaultArtDetailsDao = ArtDetailsDao(
    id = defaultArtId,
    objectNumber = defaultObjectNumber,
    title = defaultTitle,
    principalMakers = listOf(
        ArtCreator(
            defaultCreator,
            "USA",
            occupation = null,
            roles = listOf("writer")
        )
    ),
    materials = defaultMaterials,
    techniques = defaultTechniques,
    dating = Dating(defaultDatingString),
    webImage = WebImage(guid = "id23", width = 100, height = 200, url = defaultImageUrl)
)
val defaultArtDetailsResponse = ArtDetailsResponse(
    artObject = defaultArtDetailsDao
)
val defaultArtDetails = ArtDetails(
    id = defaultArtId,
    objectNumber = defaultObjectNumber,
    title = defaultTitle,
    creators = listOf(defaultCreator),
    materials = defaultMaterials,
    techniques = defaultTechniques,
    dating = defaultDatingString,
    image = ArtDetailsImage(
        url = defaultImageUrl,
        width = 100,
        height = 200
    )
)

val defaultArtObjectListItems = listOf(
    ArtObjectListItem(
        id = defaultArtId,
        objectNumber = defaultObjectNumber,
        title = defaultTitle,
        creator = defaultCreator,
        imageUrl = defaultImageUrl,
    )
)
val secondaryArtObjectListItems = listOf(
    ArtObjectListItem(
        id = "id2",
        objectNumber = "obj2",
        title = "Drawing of the office",
        creator = "Pam Beesly",
        imageUrl = "http://theoffice.co.uk/pam_drawing.png",
    )
)
val defaultCollectionResponse = CollectionResponse(
    count = 1,
    artObjects = listOf(
       ArtObjectDao(

           id = defaultArtId,
           objectNumber = defaultObjectNumber,
           title = defaultTitle,
           hasImage = true,
           principalOrFirstMaker = defaultCreator,
           webImage = WebImage(
               guid = "image2",
               url = defaultImageUrl,
               width = 100,
               height = 100,
           )
       )
    )
)