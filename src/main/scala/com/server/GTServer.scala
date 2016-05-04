package com.server

import akka.actor.ActorSystem
// import com.models.Business
import com.support.CORSSupport
import com.services.GTRetrievalService
import spray.http.MediaTypes
import spray.routing.{Route, SimpleRoutingApp}

object GTServer extends App with SimpleRoutingApp with CORSSupport{


  implicit val actorSystem = ActorSystem()


  //Custom directive to replace the inclcusion of the stated return type header
  def getJson(route: Route) = get{
    respondWithMediaType(MediaTypes.`application/json`){
      route
    }
  }

  //I have defined each route independently as lazy vals to keep the code clean

  //Endpoint: List avalable endpoints
  lazy val helloRoute = get {
      cors{
        path("hello") {
          complete {
            "Welcome to the MicroDG Geo Tagging Service" +
            "\n Routes:" +
            "\n Retrieve Lat & Long: geoTaggings/addresses/{address}}" +
            "\n Retrieve Latitude: geoTaggings/addresses/latitudes/{address}}" +
            "\n Retrieve Longitude: geoTaggings/addresses/longitudes/{address}}"
          }
        }
      }
  }
  
  //Endpoint: Return the latitude and longitude for a given address
  lazy val getGeoTagRoute = getJson {
      cors{
        path("geoTaggings" / "addresses" / Segment) { address =>
          complete {
            GTRetrievalService.getGeoTags_single(address)
          }
        }
      }
  }
  
  //Endpoint: Return the latitude for a given address
  lazy val getGeoLatRoute = getJson {
      cors{
        path("geoTaggings" / "addresses" / "latitudes" / Segment) { address =>
          complete {
            GTRetrievalService.getGeoLats_single(address)
          }
        }
      }
  }
  
  //Endpoint: Return the longitude for a given address
  lazy val getGeoLngtRoute = getJson {
      cors{
        path("geoTaggings" / "addresses" / "longitudes" / Segment) { address =>
          complete {
            GTRetrievalService.getGeoLngs_single(address)
          }
        }
      }
  }

  startServer(interface = "0.0.0.0", port = 8080) {
    helloRoute~
    getGeoTagRoute~
    getGeoLatRoute~
    getGeoLngtRoute
  }

}