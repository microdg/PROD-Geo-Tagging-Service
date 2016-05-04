    package com.support

import net.liftweb.json._
import net.liftweb.json.Serialization.write

object GTRetrievalHelper {
    
    implicit val formats = DefaultFormats
    
       //Function: A helper function for parsing out the Latitude value
       def getLatitude(json: JValue): String = {

            val jLat = json find {
                     case JField("lat", _) => true
                     case _ => false
                  }
            
            val sLat = write(jLat)
            val lat =  sLat.substring(6, sLat.length)
            return lat
        }
        
        //Function: A helper function for parsing out the Longitude value
        def getLongitude(json: JValue): String = {

            val jLng = json find {
                     case JField("lng", _) => true
                     case _ => false
                   }
            
            val sLng = write(jLng)
            val lng =  sLng.substring(6, sLng.length)
            return lng
        }
        
        
        //Function: A helper function for constructing a a url
        def urlConstructor(address: String) : String = {
            val apiEndpoint = "https://maps.googleapis.com/maps/api/geocode/json?address="
            val apiKey = "&key=AIzaSyCW6S4V0IgkgoXwsgZUtdTTNRzM2zPqe9E"
            val formattedAddress = address.replace(" ", "+")
            val url = apiEndpoint + formattedAddress + apiKey
            return url
        }
           
}