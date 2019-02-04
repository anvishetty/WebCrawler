# WebCrawler
Web Crawler to get recipe information from https://www.yummly.com/recipes

# Getting Started

The project is created using 2 classes.

App Class: contains the main method and is the starting point for the project. You can run the webCrawler from here. 

webCrawler Class: This contains all the methods and project structure to make the webcrawler run.
There are 3 methods: Crawl(), extractData() and filterAndAdd(). 

2 global variables that have been initialised are pagesVisited and pagesToVisit which are both declared as Sets. This is because we dont want to visit the same page again and again. Hence to avoid duplicate URLs we are using Sets. 

Crawl()-we pass the URL and the output file here as parameters. Connection is established in this method and we also call the extractData() and the filterAndAdd() methods here. 

extractData()- this method helps in extracting the data required after we establish connection with the URL. We will pass the webDocument and the a PrintWriter to this method. We converted the data to JSON format to make it more readable. We checked in the page source and recognized how much data needs to be removed and we did this by using a string builder. The parser will parse through the string and then get a JSON Array which contains all the attributes we are looking for. Then based on the requirements of this project we looked for data and printed it to the output text file. 

filterAndAdd()- filters the URLs by only referencing those which have "recipe" in them. 

# Pre Requisites
The program can run on any IDE with java installed.
I used intelliJ to create this webcrawler and had to add 3 dependencies 2 from Maven. GSON and JSON and a JSOUP dependency to establish the connection. 

# Tests and Enhancements

In Crawl() you can pass different URLs as the project is not tied to one URL. Hence we can crawl multiple websites and get the required information. 
In filterAndAdd() you can change the keyword you are trying to filter by depending on the website used and the requirements of the project.



