This is modified README which is based on the original DL-Coding-Challenge-Pulibc README file

# DroidWeather
Welcome to Droid Weather
Droid Weather is finished in 4 hours in DL coding challenge. It is a fully funcational weather app that allows you to checkout weather information based your current location. 

Features
---
* Automatically geolookup
* Fetching weather condition through www.wunderground.com's weather API
* Up to 3 days forcast including day and night
* Rich weather format, including UV index, precipitation, humidity, pressure, wind and feels-like

Architecture
---
Droid Weather is built on a MVP (model-view-presenter) architecture. In model layer, the low level network interactor sends http request to API endpoint and fetch the raw data back to presenter. Presenter is an intermidiante layer that analyse, process and package the data so the data are ready to be present in the view layer. The view is the top layer that concentrates only UI event. 
MVP makes everything more clear so that development can be done in hours. 

UI
---
Due to the time limit, only a simple UI is designed. There's a main view with a slide up panel. The slide-up panel is grabbed from 3rd party package. Current weather condition is presented in the main view, forecast and detailed weather info are in the slide up panel. When the panel is in collapse status, only a row of forecast infomation is shown at the bottom of the screen, user can slide the panel up to view more detailed weather conditions. The forecast slot and detailed condition slots are GridView and ListView respectively, fed by two customized adapters. The original thinking including adding a radar plot in the slide-up panel, however, it is not support for entry level API, I have to purchase higher level API to get it done. So I didn't go with that, but it's definitely doable.

Third party libraries
---
* Android sliding up pannel: 3.3.0  			---> https://github.com/umano/AndroidSlidingUpPanel
* Android Asynchronous Http Client: 1.4.9 		---> http://loopj.com/android-async-http/
* ButterKnife: 7.0.1					---> http://jakewharton.github.io/butterknife/
* Glide: 3.70						---> https://github.com/bumptech/glide

Note
---
There are two screenshots can be found at the root of the repository

Original document
---

# DL-Coding-Challenge
Welcome to the Detroit Labs coding challenge. 

Please fork this repo and create a branch that includes your name in the branch name (We like to read commits!). Choose from Android, iOS or web to complete this challenge. Comment on what libraries or frameworks you choose to use so we know exactly what code is yours and what is coming from a third-party.

Plan, document, and build your proof of concept then [submit](#submit) it when you are done. 

Proof of Concept scenario description
---
Your Project Manager has just come to you with a potential Client who has requested a WeatherApp _Proof of Concept_! The Client has given us a budget and a laundry list of features. It is your job to build from the feature list to your skill level and defend your implementaton and design decisions. We are trying to win more business from this Very Important Client, and this Proof of Concept needs to impress them in order to move forward. The good news is you have a lot of flexibility with the UI and how you want to display the Weather information. 

The Challenge is to implement features and a UI for a Proof of Concept that looks good and gives insight into what the full app could be in the future.

How many and what features you implement is up to you, but be ready to discuss your strategy and implementation.

* In the [Non-Functional Requirements of Interest](#platformSpecs) section you can find more details about what we are looking for. 
* Please use the [Weather Underground API][1] (There is a lot in the API, show us what you got!).

Ideas for Features
---
* Current Location Weather
* Icons & Menu Icons
* Monthly, Weekly, Daily, Hourly Weather Info
* Map (weather/radar)
* Methods for Multiple UI Themes 
* Pull to Refresh
* Seasonal Conditions (Golf, Sailing, Skiing) 
* Wearable App
* User can display multiple locations 
* User Auth
* Graph (Precipitation, Humidity, Temp, etc.)
* GPS Location
  
*notes* 
* Feel free to add any of your own features, just be prepared to defend them!

<a id="platformSpecs"></a>

Non-Functional Requirements of Interest:
---
* <a id="baciSpec"></a>**Basics**
	* Robust UI (You're probably not a designer. Robust is code for "uses platform standards and standard platform controls correctly and consistently")
	* Understanding of performing asynchronous tasks (thread management and the like)
	* Menu drill down (ex. Food Menu -> Drink Items -> Scroll Through Sodas)
	* Error handling
	* Data persistance (Your own backend or a Service)
	* Multiple endpoints updating a single UI
	* ListView/TableView
		* Different Types of Cells (Height, Editable, add, delete etc.)
* <a id="androidSpec"></a>**Android**
	* Understanding of Fragments and Adapters
* <a id="iosSpec"></a>**iOS**
	* Understanding of Tables

Languages and Frameworks we call friends:
---
* **Languages**
	* Java
	* Ruby
	* JavaScript
	* Php
	* Objective-C
	* Swift
* **Frameworks**
	* Android
	* iOS
	* Sinatra
	* Ruby On Rails
	* Cake
	* Angular.js
* **Other Technologies**
	* Parse
	* Node.js

Our Evaluation Criteria:
---
* Code Quality, Style, and Readablity
* Architecture and Design Decisions (Usable UX, number of features selected to implement, implementation mechanics, patterns used, clever vs. simple)
* Standard Practices (documentation, comments, commit History)
* Completeness of Features Chosen
* UX & Design
* Automated Testing (optional, but if you know how, it'd be nice to see)

**Remember**: this exercise is meant to give you many options and choices in the best way for you to demonstrate, highlight, and communicate your programming ability, design experience, personal style, and coding preferences. We are also looking for how you manage and decide what is important, given a limited amount of time to complete an overwhelming task. *You are not expected to complete this app*, but rather be able to make decisions on what to do with this challenge in the time allotted, describe that decision making process, and then show your work. Getting more done is sometimes better, but if you are able to do a lot of work at a low level of quality and completeness or a little bit of work at a high level of quality and completeness, make an informed choice on which way to go and be prepared to talk about that. Let the challenge scenario guide you.

<a id="submit"></a>
How to Submit
---
* Please fork this repository and create a branch for your project. 
* When you are finished, send a pull request for review.
* Please make sure any services you may use are deployed so we can test your app.
* Once we have reviewed your submission we will contact you for the next steps.

Thank you for applying to Detroit Labs!

<!-- External Links...Reference by Number -->
[1]:http://www.wunderground.com/weather/api/d/docs
