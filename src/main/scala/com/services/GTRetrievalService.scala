package com.services

import com.support.LoggingSupport
import com.support.GTRetrievalHelper

import java.io.{IOException}
import scala.io.{Source}
import java.net.{URL, HttpURLConnection, SocketTimeoutException}

import net.liftweb.json._
import net.liftweb.json.Serialization.write
import java.util.Calendar
import net.liftweb.json.JsonDSL._

object GTRetrievalService {
    
    implicit val formats = DefaultFormats
    
    //Here I am defining a case class for each result object
    case class GeoCords(lat: String, lng: String)
    case class GeoLats(lat: String)
    case class GeoLngs(lng: String)
    
    
        def getGeoTags_single(address: String, connectTimeout: Int = 4000, readTimeout: Int = 4000, requestMethod: String = "GET") : String = {
            
            //Logging: Log the service request
            LoggingSupport.serviceRequestlog1("Geo Tagging Service", address)
            
            //Construct url using helper function
            val url = GTRetrievalHelper.urlConstructor(address)
            
            //Establish connection to Googles Geo Coding web service
            val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
            println(connection)
            connection.setConnectTimeout(connectTimeout)
            connection.setReadTimeout(readTimeout)
            connection.setRequestMethod(requestMethod)
            
            val inputStream = connection.getInputStream
            //Parse the connction.InputStream to JSON
            val content = io.Source.fromInputStream(inputStream).mkString

            if (inputStream != null) inputStream.close
            
            val json = parse(content)
            
            //Retrieve the desired values
            val lat = GTRetrievalHelper.getLatitude(json)
            val lng = GTRetrievalHelper.getLongitude(json)
            //Create a new object with the retrieved values as paramters
            val geoCords = List[GeoCords](GeoCords(s"$lat",s"$lng"))
            //Convert the objet back to JSON
            val jsonString = write(geoCords)
            //Return JSON Object
            return jsonString
        }
        
        def getGeoLats_single(address: String, connectTimeout: Int = 4000, readTimeout: Int = 4000, requestMethod: String = "GET") : String = {
            
            //Logging: Log the service request
            LoggingSupport.serviceRequestlog1("Geo Tagging Service", address)
            
            //Construct url using helper function
            val url = GTRetrievalHelper.urlConstructor(address)
            
            //Establish connection to Googles Geo Coding web service
            val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
            println(connection)
            connection.setConnectTimeout(connectTimeout)
            connection.setReadTimeout(readTimeout)
            connection.setRequestMethod(requestMethod)
            
            
            val inputStream = connection.getInputStream
            //Parse the connction.InputStream to JSON
            val content = io.Source.fromInputStream(inputStream).mkString

            if (inputStream != null) inputStream.close
            
            val json = parse(content)
        
            //Retrieve the desired value
            val lat = GTRetrievalHelper.getLatitude(json)
            //Convert the objet back to JSON
            val jsonString = write(lat)
            //Return JSON Object
            return jsonString
        }
        
        def getGeoLngs_single(address: String, connectTimeout: Int = 4000, readTimeout: Int = 4000, requestMethod: String = "GET") : String = {
            
            //Logging: Log the service request
            LoggingSupport.serviceRequestlog1("Geo Tagging Service", address)
            
            //Construct url using helper function
            val url = GTRetrievalHelper.urlConstructor(address)
            
            //Establish connection to Googles Geo Coding web service
            val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
            println(connection)
            connection.setConnectTimeout(connectTimeout)
            connection.setReadTimeout(readTimeout)
            connection.setRequestMethod(requestMethod)
            
            
            val inputStream = connection.getInputStream
            //Parse the connction.InputStream to JSON
            val content = io.Source.fromInputStream(inputStream).mkString

            if (inputStream != null) inputStream.close
            
            val json = parse(content)
            
            //Retrieve the desired values
            val lng = GTRetrievalHelper.getLongitude(json)
            //Convert the objet back to JSON
            val jsonString = write(lng)
            //Return JSON Object
            return jsonString
        }
        
    
}