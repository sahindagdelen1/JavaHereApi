[![Build Status](https://travis-ci.org/sahindagdelen/JavaHereApi.svg?branch=master)](https://travis-ci.org/sahindagdelen/JavaHereApi)
![npm](https://img.shields.io/npm/l/express.svg)
![GitHub last commit](https://img.shields.io/github/last-commit/google/skia.svg)


JavaHEREApi Java Client Library  :coffee:
=================================

Java library for the HERE Api. (https://developer.here.com/develop/rest-apis)
  
 
### Requirements
* JDK 1.6 and up.

## Usage

GeocoderApi,ReverseGeocoderApi, MultireverseGeocoderApi,SearchApi (Places Api)  supported,  appId and appCode parameters must be used to call methods.
JavaHereApi returns json as String, it any error occurs empty String returns. 
Appid and appcode are acquired signing up developer.here.com and choose free plan. There is a time limit which is 90 days for appid and appcode validity.

Example usage 
------------

GeocoderApi geoCoder =new GeocoderApi(myAppId,myAppCode);
String geoCoderResponse = geoCoder.partialAddresInfo("425","randolph","chicago","usa","8");
 

Installation
====================


Manual installation
-------------------

Clone the repository, and use

```
    mvn package
```


If you would like a copy of the javadocs, use

```
    mvn javadoc:javadoc
```

Run tests
```
    mvn test
```


Maven Installation
------------------

Add the following dependency to your pom.xml

```  
<!-- JavaHEREApi Dependency-- -->
<dependency>
    <groupId>com.github.sahindagdelen</groupId>
    <artifactId>JavaHereApi</artifactId>
    <version>VERSION</version>
<!-- Replace VERSION with the version you want to use -->
</dependency>

```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)

