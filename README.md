# RenewNSell

RenewNSell simplifies the sale of brand-new defective products by connecting brands with buyers seeking discounted items due to faults.

## Class Diagram
![final project (3) drawio (1)](https://github.com/mmyh147/RenewNSell/assets/61750916/74e21d67-9b27-47cf-8486-78bdd1fc397a)


## Use Case
![UseCaseFinalProject (4) drawio](https://github.com/mmyh147/RenewNSell/assets/61750916/13549297-69cf-4589-b727-8b6487cbe869)




## Figma Link

You can view the Figma design [here](https://www.figma.com/file/UCHM3h9HjDvOea3peiIJvg/Untitled?type=design&mode=design&t=kRawrEPgMJYnhvna-0).

## Presentation Link

You can view the presentation [here](https://www.canva.com/design/DAGEipSxZN0/646HXCCE4gTsssZVUNkWrw/edit?utm_content=DAGEipSxZN0&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton).







# My Work

## Project Setup and Management
- Created RenewNSell project.
- Created and managed GitHub repository, merging all branches.

## Entity and Relationship
- Established relationships between entities.
- Implemented Spring Security for authentication and authorization.

## Entities
- Implemented User Entity.
- Implemented Customer Entity.
- Implemented Company Entity.

## CRUD Operations
- Implemented CRUD operations for User.
- Implemented CRUD operations for Customer.
- Implemented CRUD operations for Company.







# My Endpoints



## User Management
- `customerRegister()`: Register new customer
- `companyRegister()` : Register new company
- `deleteMyUser()`: Delete the current user.
- `getUserById(): User`: Retrieve a user by their ID.
- `getUserByUsername(): User`: Retrieve a user by their username.
- `login()`: Perform user login.
- `logout()`: Perform user logout.
- `updateMyCustomer()`: Update current user's customer information.
- `getCustomerById(): User`: Retrieve a customer by their ID.
- `getCompanyById(): User`: Retrieve a company by their ID.
- `updateMyCompany()`: Update current user's company information.

## Company Analysis
- `totalRevenueForCompany(): double`: Calculate the total revenue for the company.
- `todayRevenueForCompany(): double`: Calculate today's revenue for the company.
- `currentMonthRevenueForCompany(): double`: Calculate the current month's revenue for the company.
- `lastMonthRevenueForCompany(): double`: Calculate last month's revenue for the company.
- `countTotalProductSoldForCompany(): int`: Count total products sold for the company.
- `countTodayProductSoldForCompany(): int`: Count products sold today for the company.
- `countCurrentMonthProductSoldForCompany(): int`: Count products sold this month for the company.
- `countLastMonthProductSoldForCompany(): int`: Count products sold last month for the company.

## Website Analysis
- `totalRevenueForWebsite(): double`: Calculate the total revenue for the website.
- `todayRevenueForWebsite(): double`: Calculate today's revenue for the website.
- `currentMonthRevenueForWebsite(): double`: Calculate the current month's revenue for the website.
- `lastMonthRevenueForWebsite(): double`: Calculate last month's revenue for the website.
- `countTotalProductSoldForWebsite(): int`: Count total products sold for the website.
- `countTodayProductSoldForWebsite(): int`: Count products sold today for the website.
- `countCurrentMonthProductSoldForWebsite(): int`: Count products sold this month for the website.
- `countLastMonthProductSoldForWebsite(): int`: Count products sold last month for the website.

