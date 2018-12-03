## Team
* Stephen Clabaugh
* Ben Lehman
* Christopher Roadcap
* Courtney Rush
* Kanza Amin
* Henry Watt 
* Nahesha Paulection


# Persist Method
* Set all instance variables, then call a persist() method to save changes to the database


# Gateway Naming Conventions
* TableNameGateway
 

# CLASS LAYOUT
* Static methods at the top(TableDataGateway Functionality)(Find All By X)  *USE FIND NOT GET*
* Instance Variables
* Create/Find Constructors (create constructor - (Find - sets all instance variables by querying by ID)
* Getters/Setters
* Non-static methods(Find by unique)(returns 1 row)
* Persists
* (private)Insert method


# Uniformity
* Plural Gateways (aka ToolsGateway)
* Singular DTOs (aka ToolDTO)


# Project 2 ToDo List
* Test All Mappers (Kanza)
* Fix Identity Maps (Ben)
* Fix Lazy Load and related Mappers 
* Remove findByUPC and findByManufacturerID from mappers
* COMMENTS






