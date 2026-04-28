CREATE database [Practice2026]
USE [Practice2026]
GO
DROP TABLE IF EXISTS Logins
DROP TABLE IF EXISTS Employees
DROP TABLE IF EXISTS Equipment_To_Categories
DROP TABLE IF EXISTS Categories
DROP TABLE IF EXISTS RentalPayments
DROP TABLE IF EXISTS Rentals
DROP TABLE IF EXISTS Equipment
DROP TABLE IF EXISTS Customers
DROP TABLE IF EXISTS [ADDRESS]

CREATE TABLE [dbo].[Address](
	[ID] [int] NOT NULL,
	[Address1] [varchar](255) NULL,
	[Address2] [varchar](255) NULL,
	[PostalCode] [char](6) NULL,
	[City] [varchar](150) NULL,
	[Province] [char](2) NULL,
PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categories](
	[ID] [int] NOT NULL,
	[Description] [varchar](100) NULL,
	[Name] [varchar](75) NULL,
PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customers](
	[CustomerID] [int] NOT NULL,
	[FirstName] [varchar](50) NULL,
	[LastName] [varchar](50) NULL,
	[Email] [varchar](100) NULL,
	[Phone] [varchar](15) NULL,
	[AddressID] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[CustomerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employees](
	[EmployeeID] [int] NOT NULL,
	[FirstName] [varchar](50) NULL,
	[LastName] [varchar](50) NULL,
	[Email] [varchar](100) NULL,
	[Phone] [varchar](15) NULL,
	[AddressID] [int] NULL,
	[HireDate] [date] NULL,
PRIMARY KEY CLUSTERED
(
	[EmployeeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Equipment](
	[EquipmentID] [int] NOT NULL,
	[EquipmentName] [varchar](75) NULL,
	[Description] [varchar](500) NULL,
	[RentalRate] [money] NULL,
	[Available] [bit] NULL,
PRIMARY KEY CLUSTERED
(
	[EquipmentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Equipment_To_Categories](
	[EquipmentID] [int] NOT NULL,
	[CategoryID] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[EquipmentID] ASC,
	[CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RentalPayments](
	[PaymentID] [int] NOT NULL,
	[RentalID] [int] NULL,
	[Amount] [money] NULL,
	[PaymentDate] [date] NULL,
PRIMARY KEY CLUSTERED
(
	[PaymentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Rentals](
	[RentalID] [int] NOT NULL,
	[CustomerID] [int] NULL,
	[EquipmentID] [int] NULL,
	[RentalDate] [date] NULL,
	[ReturnDate] [date] NULL,
PRIMARY KEY CLUSTERED
(
	[RentalID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (1, N'123 Main St', N'Apt 101', N'M1M1M1', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (2, N'456 Elm St', NULL, N'V5V5V5', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (3, N'789 Oak St', N'Suite 201', N'K2K2K2', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (4, N'321 Maple Ave', NULL, N'L3L3L3', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (5, N'555 Pine St', N'Unit B', N'T4T4T4', N'Calgary', N'AB')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (6, N'777 Cedar St', NULL, N'R6R6R6', N'Calgary', N'AB')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (7, N'999 Birch Rd', N'Apt 301', N'G7G7G7', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (8, N'222 Spruce Ave', NULL, N'S9S9S9', N'Calgary', N'AB')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (9, N'444 Birch St', N'Suite 501', N'H8H8H8', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (10, N'888 Elm St', NULL, N'N2N2N2', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (11, N'111 Cedar Ave', N'Unit C', N'B3B3B3', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (12, N'333 Oak Rd', NULL, N'P1P1P1', N'Calgary', N'AB')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (13, N'666 Maple St', N'Apt 401', N'Y5Y5Y5', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (14, N'777 Spruce Blvd', NULL, N'W2W2W2', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (15, N'999 Pine Ave', N'Suite 601', N'E4E4E4', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (16, N'222 Oak St', NULL, N'T3T3T3', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (17, N'444 Cedar Ave', N'Suite 701', N'V8V8V8', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (18, N'666 Elm Rd', NULL, N'R4R4R4', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (19, N'888 Maple Blvd', N'Unit D', N'M5M5M5', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (20, N'111 Pine Ave', NULL, N'C6C6C6', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (21, N'333 Birch St', N'Apt 501', N'G3G3G3', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (22, N'555 Spruce Rd', NULL, N'W5W5W5', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (23, N'777 Oak St', N'Suite 801', N'K9K9K9', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (24, N'999 Cedar Blvd', NULL, N'S7S7S7', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (25, N'123 Elm Ave', N'Unit E', N'V2V2V2', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (26, N'456 Maple St', NULL, N'L8L8L8', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (27, N'789 Birch Rd', N'Apt 901', N'B7B7B7', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (28, N'222 Pine Blvd', NULL, N'E3E3E3', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (29, N'444 Spruce Ave', N'Suite 1001', N'H4H4H4', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (30, N'666 Oak Rd', NULL, N'N6N6N6', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (31, N'123 Maple St', N'Suite 1101', N'T8T8T8', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (32, N'456 Birch Ave', NULL, N'V9V9V9', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (33, N'789 Cedar Rd', N'Unit F', N'R1R1R1', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (34, N'999 Elm Blvd', NULL, N'M2M2M2', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (35, N'111 Oak St', N'Apt 1201', N'C5C5C5', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (36, N'333 Pine Ave', NULL, N'G6G6G6', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (37, N'555 Birch St', N'Suite 1301', N'W7W7W7', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (38, N'777 Cedar Ave', NULL, N'S3S3S3', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (39, N'999 Maple Rd', N'Unit G', N'K8K8K8', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (40, N'123 Spruce Blvd', NULL, N'V3V3V3', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (41, N'456 Oak St', N'Apt 1401', N'L9L9L9', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (42, N'789 Elm Ave', NULL, N'N4N4N4', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (43, N'999 Birch Rd', N'Suite 1501', N'B6B6B6', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (44, N'111 Cedar Blvd', NULL, N'E1E1E1', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (45, N'333 Spruce St', N'Unit H', N'H2H2H2', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (46, N'555 Maple Ave', NULL, N'P5P5P5', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (47, N'777 Pine Rd', N'Suite 1601', N'Y3Y3Y3', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (48, N'999 Oak Blvd', NULL, N'R7R7R7', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (49, N'123 Cedar St', N'Apt 1701', N'T1T1T1', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (50, N'456 Spruce Ave', NULL, N'W4W4W4', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (51, N'789 Maple Blvd', N'Unit I', N'M3M3M3', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (52, N'999 Elm St', NULL, N'K6K6K6', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (53, N'111 Birch Ave', N'Suite 1801', N'C8C8C8', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (54, N'333 Cedar Rd', NULL, N'G1G1G1', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (55, N'555 Oak Ave', N'Apt 1901', N'W9W9W9', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (56, N'777 Pine Blvd', NULL, N'S5S5S5', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (57, N'999 Spruce St', N'Unit J', N'B9B9B9', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (58, N'123 Maple Rd', NULL, N'E2E2E2', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (59, N'456 Cedar Blvd', N'Suite 2001', N'H3H3H3', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (60, N'789 Elm St', NULL, N'N5N5N5', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (61, N'123 Main St', NULL, N'M1M1M1', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (62, N'456 Elm St', NULL, N'V5V5V5', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (63, N'789 Oak St', N'Apt 101', N'K2K2K2', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (64, N'101 Pine St', NULL, N'C3C3C3', N'Calgary', N'AB')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (65, N'555 Maple Ave', NULL, N'H4H4H4', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (66, N'777 Cedar Rd', NULL, N'R6R6R6', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (67, N'888 Birch Blvd', NULL, N'T7T7T7', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (68, N'999 Willow Way', NULL, N'E8E8E8', N'Calgary', N'AB')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (69, N'222 Spruce St', N'Suite 200', N'L9L9L9', N'Regina', N'SK')
INSERT [dbo].[Address] ([ID], [Address1], [Address2], [PostalCode], [City], [Province]) VALUES (70, N'333 Ash Ave', NULL, N'S0S0S0', N'Regina', N'SK')
GO
INSERT [dbo].[Categories] ([ID], [Description], [Name]) VALUES (1, N'Tools used for cutting materials such as wood, metal, or plastic.', N'Sawing Tools')
INSERT [dbo].[Categories] ([ID], [Description], [Name]) VALUES (2, N'Tools used for drilling holes in various materials such as wood, metal, or concrete.', N'Drilling Tools')
INSERT [dbo].[Categories] ([ID], [Description], [Name]) VALUES (3, N'Tools used for breaking or demolishing materials such as concrete, masonry, or asphalt.', N'Demolition Tools')
INSERT [dbo].[Categories] ([ID], [Description], [Name]) VALUES (4, N'Tools used for grinding, sanding, or polishing surfaces.', N'Grinding Tools')
INSERT [dbo].[Categories] ([ID], [Description], [Name]) VALUES (5, N'Tools used for trimming, finishing or shaping materials.', N'Finishing Tools')
INSERT [dbo].[Categories] ([ID], [Description], [Name]) VALUES (6, N'Accessories and attachments for power tools.', N'Power Tool Accessories')
INSERT [dbo].[Categories] ([ID], [Description], [Name]) VALUES (7, N'Tools for fastening material together', N'Fastner Tools')
INSERT [dbo].[Categories] ([ID], [Description], [Name]) VALUES (8, N'Hand Tools: These require no power', N'Hand Tools')
INSERT [dbo].[Categories] ([ID], [Description], [Name]) VALUES (9, N'Landscaping tools, shape your outside world', N'Landscaping Tools')
GO
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (1, N'John', N'Doe', N'john@example.com', N'555-123-4567', 1)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (2, N'Jane', N'Smith', N'jane@example.com', N'555-987-6543', 2)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (3, N'David', N'Johnson', N'david@example.com', N'555-321-7890', 3)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (4, N'Sarah', N'Williams', N'sarah@example.com', N'555-654-3210', 4)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (5, N'Michael', N'Brown', N'michael@example.com', N'555-135-2468', 5)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (6, N'Emily', N'Davis', N'emily@example.com', N'555-864-2093', 6)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (7, N'Daniel', N'Martinez', N'daniel@example.com', N'555-246-8013', 7)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (8, N'Jessica', N'Anderson', N'jessica@example.com', N'555-678-9012', 8)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (9, N'Christopher', N'Taylor', N'chris@example.com', N'555-890-1234', 9)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (10, N'Amanda', N'Thomas', N'amanda@example.com', N'555-456-7890', 10)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (11, N'Matthew', N'Lee', N'matthew@example.com', N'555-234-5678', 11)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (12, N'Ashley', N'Garcia', N'ashley@example.com', N'555-789-0123', 12)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (13, N'Ryan', N'Rodriguez', N'ryan@example.com', N'555-012-3456', 13)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (14, N'Taylor', N'Lopez', N'taylor@example.com', N'555-678-9012', 14)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (15, N'Lauren', N'Hernandez', N'lauren@example.com', N'555-901-2345', 15)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (16, N'Eric', N'Wilson', N'eric@example.com', N'555-345-6789', 16)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (17, N'Samantha', N'Moore', N'samantha@example.com', N'555-987-6543', 17)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (18, N'Kevin', N'Clark', N'kevin@example.com', N'555-234-5678', 18)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (19, N'Olivia', N'Young', N'olivia@example.com', N'555-876-5432', 19)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (20, N'Justin', N'King', N'justin@example.com', N'555-456-7890', 20)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (21, N'Hannah', N'Evans', N'hannah@example.com', N'555-765-4321', 21)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (22, N'Brandon', N'Hill', N'brandon@example.com', N'555-321-0987', 22)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (23, N'Alexis', N'Parker', N'alexis@example.com', N'555-654-3210', 23)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (24, N'Jordan', N'Gonzalez', N'jordan@example.com', N'555-210-9876', 24)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (25, N'Rachel', N'Allen', N'rachel@example.com', N'555-543-2109', 25)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (26, N'Nathan', N'Scott', N'nathan@example.com', N'555-789-0123', 26)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (27, N'Madison', N'Carter', N'madison@example.com', N'555-098-7654', 27)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (28, N'Tyler', N'Torres', N'tyler@example.com', N'555-321-0987', 28)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (29, N'Emma', N'Wright', N'emma@example.com', N'555-876-5432', 29)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (30, N'Cameron', N'Lopez', N'cameron@example.com', N'555-234-5678', 30)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (31, N'Rebecca', N'Young', N'rebecca@example.com', N'555-654-3210', 31)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (32, N'Patrick', N'Hill', N'patrick@example.com', N'555-098-7654', 32)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (33, N'Christina', N'Gonzalez', N'christina@example.com', N'555-321-0987', 33)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (34, N'Jonathan', N'Scott', N'jonathan@example.com', N'555-876-5432', 34)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (35, N'Megan', N'Perez', N'megan@example.com', N'555-234-5678', 35)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (36, N'Andrew', N'King', N'andrew@example.com', N'555-901-2345', 36)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (37, N'Stephanie', N'Wright', N'stephanie@example.com', N'555-345-6789', 37)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (38, N'Nicholas', N'Lee', N'nicholas@example.com', N'555-789-0123', 38)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (39, N'Christine', N'Torres', N'christine@example.com', N'555-210-9876', 39)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (40, N'Benjamin', N'Lopez', N'benjamin@example.com', N'555-543-2109', 40)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (41, N'Victoria', N'Ward', N'victoria@example.com', N'555-678-9012', 41)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (42, N'Timothy', N'Flores', N'timothy@example.com', N'555-321-7890', 42)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (43, N'Julia', N'Morgan', N'julia@example.com', N'555-987-6543', 43)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (44, N'Gregory', N'Baker', N'gregory@example.com', N'555-456-7890', 44)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (45, N'Isabella', N'Bell', N'isabella@example.com', N'555-876-5432', 45)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (46, N'Zachary', N'Diaz', N'zachary@example.com', N'555-234-5678', 46)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (47, N'Natalie', N'Hayes', N'natalie@example.com', N'555-901-2345', 47)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (48, N'Kyle', N'Fisher', N'kyle@example.com', N'555-345-6789', 48)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (49, N'Alexandra', N'Russell', N'alexandra@example.com', N'555-789-0123', 49)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (50, N'Peter', N'Watson', N'peter@example.com', N'555-210-9876', 50)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (51, N'Cassandra', N'Reed', N'cassandra@example.com', N'555-543-2109', 51)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (52, N'Dylan', N'Bennett', N'dylan@example.com', N'555-678-9012', 52)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (53, N'Hailey', N'Gray', N'hailey@example.com', N'555-321-7890', 53)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (54, N'Ethan', N'Butler', N'ethan@example.com', N'555-987-6543', 54)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (55, N'Abigail', N'Barnes', N'abigail@example.com', N'555-456-7890', 55)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (56, N'Jacob', N'Howard', N'jacob@example.com', N'555-876-5432', 56)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (57, N'Mia', N'Coleman', N'mia@example.com', N'555-234-5678', 57)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (58, N'William', N'Bailey', N'william@example.com', N'555-901-2345', 58)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (59, N'Sophia', N'Miller', N'sophia@example.com', N'555-345-6789', 59)
INSERT [dbo].[Customers] ([CustomerID], [FirstName], [LastName], [Email], [Phone], [AddressID]) VALUES (60, N'Oliver', N'Ross', N'oliver@example.com', N'555-789-0123', 60)
GO
INSERT [dbo].[Employees] ([EmployeeID], [FirstName], [LastName], [Email], [Phone], [AddressID], [HireDate]) VALUES (1, N'John', N'Smith', N'john.smith@rent2U.com', N'123-456-7890', 61, CAST(N'2023-05-15' AS Date))
INSERT [dbo].[Employees] ([EmployeeID], [FirstName], [LastName], [Email], [Phone], [AddressID], [HireDate]) VALUES (2, N'Emily', N'Johnson', N'emily.johnson@rent2U.com', N'987-654-3210', 62, CAST(N'2023-03-20' AS Date))
INSERT [dbo].[Employees] ([EmployeeID], [FirstName], [LastName], [Email], [Phone], [AddressID], [HireDate]) VALUES (3, N'Michael', N'Williams', N'michael.williams@rent2U.com', N'555-123-4567', 63, CAST(N'2022-11-10' AS Date))
INSERT [dbo].[Employees] ([EmployeeID], [FirstName], [LastName], [Email], [Phone], [AddressID], [HireDate]) VALUES (4, N'Jessica', N'Brown', N'jessica.brown@rent2U.com', N'222-333-4444', 64, CAST(N'2024-01-05' AS Date))
INSERT [dbo].[Employees] ([EmployeeID], [FirstName], [LastName], [Email], [Phone], [AddressID], [HireDate]) VALUES (5, N'David', N'Jones', N'david.jones@rent2U.com', N'777-888-9999', 65, CAST(N'2023-08-22' AS Date))
INSERT [dbo].[Employees] ([EmployeeID], [FirstName], [LastName], [Email], [Phone], [AddressID], [HireDate]) VALUES (6, N'Sarah', N'Davis', N'sarah.davis@rent2U.com', N'444-555-6666', 66, CAST(N'2022-07-12' AS Date))
INSERT [dbo].[Employees] ([EmployeeID], [FirstName], [LastName], [Email], [Phone], [AddressID], [HireDate]) VALUES (7, N'Christopher', N'Garcia', N'christopher.garcia@rent2U.com', N'999-111-2222', 67, CAST(N'2023-02-28' AS Date))
INSERT [dbo].[Employees] ([EmployeeID], [FirstName], [LastName], [Email], [Phone], [AddressID], [HireDate]) VALUES (8, N'Samantha', N'Martinez', N'samantha.martinez@rent2U.com', N'333-222-1111', 68, CAST(N'2022-04-17' AS Date))
INSERT [dbo].[Employees] ([EmployeeID], [FirstName], [LastName], [Email], [Phone], [AddressID], [HireDate]) VALUES (9, N'Daniel', N'Rodriguez', N'daniel.rodriguez@rent2U.com', N'666-777-8888', 69, CAST(N'2023-10-30' AS Date))
INSERT [dbo].[Employees] ([EmployeeID], [FirstName], [LastName], [Email], [Phone], [AddressID], [HireDate]) VALUES (10, N'Ashley', N'Wilson', N'ashley.wilson@rent2U.com', N'111-222-3333', 70, CAST(N'2024-02-10' AS Date))
GO
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (1, N'Circular Saw', N'Heavy-duty circular saw with a 7-1/4 inch blade.', 25.0000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (2, N'Drill Driver', N'Cordless drill driver with variable speed settings.', 20.7500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (3, N'Impact Wrench', N'Electric impact wrench for heavy-duty fastening tasks.', 45.5000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (4, N'Reciprocating Saw', N'Powerful reciprocating saw for cutting through various materials.', 30.2500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (5, N'Demolition Hammer', N'Electric demolition hammer for breaking up concrete and masonry.', 62.8000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (6, N'Angle Grinder', N'Versatile angle grinder with adjustable guard for grinding and cutting.', 17.9000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (7, N'Rotary Hammer Drill', N'Heavy-duty rotary hammer drill for drilling into concrete and stone.', 35.0000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (8, N'Table Saw', N'Contractor-grade table saw with a powerful motor and large cutting capacity.', 50.0000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (9, N'Jigsaw', N'Variable-speed jigsaw for precise cutting of wood, metal, and plastic.', 55.2500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (10, N'Belt Sander', N'Heavy-duty belt sander for rapid material removal on large surfaces.', 42.5000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (11, N'Chop Saw', N'Compound miter saw for making precise crosscuts and miter cuts.', 38.7500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (12, N'Planer', N'Electric hand planer for smoothing and shaping wood surfaces.', 20.3000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (13, N'Nail Gun', N'Pneumatic nail gun for fastening nails in carpentry and framing.', 30.0000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (14, N'Router', N'Variable-speed router for shaping edges and cutting intricate patterns in wood.', 28.9000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (15, N'Cordless Chainsaw', N'Battery-powered chainsaw for pruning and light-duty cutting tasks.', 47.5000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (16, N'Concrete Vibrator', N'Electric concrete vibrator for removing air bubbles and ensuring proper concrete consolidation.', 65.2500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (17, N'Drywall Screw Gun', N'Specialized screw gun for quickly fastening drywall screws.', 22.7500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (18, N'Tile Saw', N'Wet tile saw for cutting ceramic, porcelain, and stone tiles.', 69.9900, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (19, N'Paint Sprayer', N'Airless paint sprayer for applying paint and stains to large surfaces.', 33.6000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (20, N'Floor Scraper', N'Heavy-duty floor scraper for removing adhesive, glue, and other floor coatings.', 28.7500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (21, N'Air Compressor', N'Portable air compressor for powering pneumatic tools and inflating tires.', 75.0000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (22, N'Scaffold', N'Mobile scaffold tower for safe and convenient access to elevated work areas.', 65.2500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (23, N'Laser Level', N'Self-leveling laser level for accurate alignment and layout tasks.', 45.0000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (24, N'Concrete Mixer', N'Electric concrete mixer for mixing large batches of concrete and mortar.', 42.8000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (25, N'Wet/Dry Vacuum', N'Heavy-duty wet/dry vacuum for cleaning up dust, dirt, and liquids.', 37.5000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (26, N'Heat Gun', N'Electric heat gun for stripping paint, thawing pipes, and shrinking tubing.', 27.7500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (27, N'Masonry Saw', N'Gas-powered masonry saw for cutting bricks, pavers, and concrete blocks.', 85.0000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (28, N'Floor Polisher', N'Commercial-grade floor polisher for buffing and shining hard floors.', 60.0000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (29, N'Stump Grinder', N'Gas-powered stump grinder for removing tree stumps and roots.', 55.2500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (30, N'Concrete Breaker', N'Hydraulic concrete breaker for breaking up concrete and asphalt surfaces.', 85.5000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (31, N'Drywall Lift', N'Heavy-duty drywall lift for raising and positioning drywall sheets.', 44.7500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (32, N'Screw Jack', N'Adjustable screw jack for supporting temporary scaffolding and shoring.', 32.9000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (33, N'Pole Saw', N'Telescoping pole saw for pruning branches and trimming trees at heights.', 37.0000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (34, N'Compactor Plate', N'Vibratory compactor plate for compacting soil, gravel, and asphalt.', 75.6000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (35, N'Trencher', N'Walk-behind trencher for digging trenches for irrigation, drainage, and utilities.', 55.2500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (36, N'Stud Finder', N'Electronic stud finder for locating studs and joists behind walls and ceilings.', 11.7500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (37, N'Tile Cutter', N'Manual tile cutter for straight and diagonal cuts in ceramic and porcelain tiles.', 33.2000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (38, N'Brick Saw', N'Portable brick saw for cutting bricks, pavers, and tiles with precision.', 48.5000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (39, N'Drywall Sander', N'Electric drywall sander for smoothing and finishing drywall surfaces.', 52.7500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (40, N'Paint Mixer', N'Electric paint mixer for thoroughly mixing paint and coatings in buckets and cans.', 15.9000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (41, N'Concrete Trowel', N'Walk-behind power trowel for finishing concrete surfaces to a smooth and level finish.', 80.0000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (42, N'Mortar Mixer', N'Gas-powered mortar mixer for mixing mortar, stucco, and plaster.', 65.2500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (43, N'Wallpaper Steamer', N'Electric wallpaper steamer for easily removing wallpaper and adhesive.', 35.0000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (44, N'Floor Nailer', N'Pneumatic floor nailer for fastening hardwood and engineered flooring.', 45.2500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (45, N'Framing Nailer', N'Pneumatic framing nailer for fastening framing and decking materials.', 38.5000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (46, N'Air Hose Reel', N'Retractable air hose reel for keeping pneumatic hoses organized and tangle-free.', 25.7500, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (47, N'Caulking Gun', N'Pneumatic caulking gun for dispensing caulk and sealants with precision.', 22.9000, 1)
INSERT [dbo].[Equipment] ([EquipmentID], [EquipmentName], [Description], [RentalRate], [Available]) VALUES (48, N'Paver Saw', N'Wet paver saw for cutting concrete pavers, bricks, and retaining wall blocks.', 68.5000, 1)
GO
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (1, 1)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (2, 2)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (3, 2)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (4, 1)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (5, 3)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (6, 4)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (7, 2)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (8, 1)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (9, 1)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (10, 5)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (11, 1)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (12, 5)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (13, 7)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (14, 5)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (15, 1)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (16, 5)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (17, 5)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (17, 7)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (18, 1)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (19, 5)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (20, 8)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (21, 6)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (22, 6)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (23, 5)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (23, 6)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (24, 6)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (25, 6)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (26, 6)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (27, 1)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (28, 5)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (29, 9)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (30, 3)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (30, 9)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (31, 5)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (32, 6)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (33, 1)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (33, 9)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (34, 5)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (34, 9)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (35, 9)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (36, 6)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (37, 1)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (38, 1)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (39, 5)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (40, 5)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (41, 6)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (42, 6)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (42, 9)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (43, 5)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (43, 6)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (44, 7)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (45, 7)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (46, 6)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (47, 5)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (47, 6)
INSERT [dbo].[Equipment_To_Categories] ([EquipmentID], [CategoryID]) VALUES (48, 1)
GO
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (1, 1, 314.0000, CAST(N'2023-01-10' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (2, 2, 285.0000, CAST(N'2023-02-18' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (3, 3, 230.3000, CAST(N'2023-03-15' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (4, 4, 456.7500, CAST(N'2023-04-24' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (5, 5, 386.7500, CAST(N'2023-05-28' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (6, 6, 419.9400, CAST(N'2023-06-20' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (7, 7, 199.2000, CAST(N'2023-07-09' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (8, 8, 154.5000, CAST(N'2023-09-02' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (9, 9, 262.5000, CAST(N'2023-09-22' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (10, 10, 142.1000, CAST(N'2023-10-16' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (11, 11, 513.0000, CAST(N'2023-11-12' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (12, 12, 450.0000, CAST(N'2023-12-10' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (13, 13, 227.5000, CAST(N'2023-01-20' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (14, 14, 391.5000, CAST(N'2023-02-26' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (15, 15, 595.0000, CAST(N'2023-03-25' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (16, 16, 255.0000, CAST(N'2023-04-28' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (17, 17, 172.5000, CAST(N'2023-06-04' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (18, 18, 453.6000, CAST(N'2023-07-01' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (19, 19, 411.0000, CAST(N'2023-07-26' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (20, 20, 291.0000, CAST(N'2023-08-20' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (21, 21, 107.4000, CAST(N'2023-09-16' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (22, 22, 222.0000, CAST(N'2023-10-11' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (23, 23, 391.5000, CAST(N'2023-11-09' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (24, 24, 256.8000, CAST(N'2023-12-25' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (25, 25, 300.0000, CAST(N'2023-01-14' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (26, 26, 103.7500, CAST(N'2023-03-05' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (27, 27, 70.5000, CAST(N'2023-03-31' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (28, 28, 201.6000, CAST(N'2023-04-26' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (29, 29, 137.4000, CAST(N'2023-05-21' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (30, 30, 232.5000, CAST(N'2023-06-16' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (31, 31, 316.5000, CAST(N'2023-07-11' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (32, 32, 360.0000, CAST(N'2023-08-08' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (33, 33, 210.0000, CAST(N'2023-09-09' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (34, 34, 331.5000, CAST(N'2023-10-07' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (35, 35, 136.5000, CAST(N'2023-11-07' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (36, 36, 271.5000, CAST(N'2023-12-10' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (37, 37, 150.0000, CAST(N'2023-01-23' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (38, 38, 331.5000, CAST(N'2023-02-18' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (39, 39, 166.5000, CAST(N'2023-03-21' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (40, 40, 173.4000, CAST(N'2023-04-16' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (41, 41, 210.0000, CAST(N'2023-05-14' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (42, 42, 268.5000, CAST(N'2023-06-12' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (43, 43, 231.0000, CAST(N'2023-07-09' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (44, 44, 270.0000, CAST(N'2023-08-07' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (45, 45, 95.4000, CAST(N'2023-09-09' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (46, 46, 181.5000, CAST(N'2023-10-07' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (47, 47, 199.2000, CAST(N'2023-11-13' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (48, 48, 391.5000, CAST(N'2023-12-11' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (49, 49, 331.5000, CAST(N'2023-01-18' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (50, 50, 291.0000, CAST(N'2023-02-13' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (51, 51, 172.5000, CAST(N'2023-03-11' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (52, 52, 222.0000, CAST(N'2023-04-08' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (53, 53, 510.0000, CAST(N'2023-05-07' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (54, 54, 285.0000, CAST(N'2023-06-11' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (55, 55, 255.0000, CAST(N'2023-07-09' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (56, 56, 154.5000, CAST(N'2023-08-13' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (57, 57, 450.0000, CAST(N'2023-09-10' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (58, 58, 391.5000, CAST(N'2023-10-08' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (59, 59, 225.0000, CAST(N'2023-11-12' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (60, 60, 376.8000, CAST(N'2023-12-10' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (61, 61, 300.0000, CAST(N'2023-01-07' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (62, 62, 391.5000, CAST(N'2023-02-11' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (63, 63, 331.5000, CAST(N'2023-03-11' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (64, 64, 121.8000, CAST(N'2023-04-08' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (65, 65, 513.0000, CAST(N'2023-05-07' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (66, 66, 201.6000, CAST(N'2023-06-11' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (67, 67, 70.5000, CAST(N'2023-07-09' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (68, 68, 232.5000, CAST(N'2023-08-13' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (69, 69, 360.0000, CAST(N'2023-09-10' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (70, 70, 137.4000, CAST(N'2023-10-08' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (71, 71, 180.0000, CAST(N'2023-11-12' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (72, 72, 255.0000, CAST(N'2023-12-10' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (73, 73, 172.5000, CAST(N'2023-01-07' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (74, 74, 222.0000, CAST(N'2023-02-11' AS Date))
INSERT [dbo].[RentalPayments] ([PaymentID], [RentalID], [Amount], [PaymentDate]) VALUES (75, 75, 166.5000, CAST(N'2023-03-11' AS Date))
GO
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (1, 12, 5, CAST(N'2023-01-05' AS Date), CAST(N'2023-01-10' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (2, 28, 15, CAST(N'2023-02-12' AS Date), CAST(N'2023-02-18' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (3, 3, 32, CAST(N'2023-03-08' AS Date), CAST(N'2023-03-15' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (4, 45, 22, CAST(N'2023-04-17' AS Date), CAST(N'2023-04-24' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (5, 7, 9, CAST(N'2023-05-21' AS Date), CAST(N'2023-05-28' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (6, 35, 18, CAST(N'2023-06-14' AS Date), CAST(N'2023-06-20' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (7, 19, 37, CAST(N'2023-07-03' AS Date), CAST(N'2023-07-09' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (8, 51, 46, CAST(N'2023-08-27' AS Date), CAST(N'2023-09-02' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (9, 16, 25, CAST(N'2023-09-15' AS Date), CAST(N'2023-09-22' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (10, 42, 12, CAST(N'2023-10-09' AS Date), CAST(N'2023-10-16' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (11, 2, 30, CAST(N'2023-11-06' AS Date), CAST(N'2023-11-12' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (12, 54, 21, CAST(N'2023-12-04' AS Date), CAST(N'2023-12-10' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (13, 10, 3, CAST(N'2023-01-15' AS Date), CAST(N'2023-01-20' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (14, 48, 42, CAST(N'2023-02-20' AS Date), CAST(N'2023-02-26' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (15, 25, 27, CAST(N'2023-03-18' AS Date), CAST(N'2023-03-25' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (16, 37, 10, CAST(N'2023-04-22' AS Date), CAST(N'2023-04-28' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (17, 5, 20, CAST(N'2023-05-29' AS Date), CAST(N'2023-06-04' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (18, 29, 34, CAST(N'2023-06-25' AS Date), CAST(N'2023-07-01' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (19, 11, 48, CAST(N'2023-07-20' AS Date), CAST(N'2023-07-26' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (20, 39, 38, CAST(N'2023-08-14' AS Date), CAST(N'2023-08-20' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (21, 17, 6, CAST(N'2023-09-10' AS Date), CAST(N'2023-09-16' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (22, 43, 33, CAST(N'2023-10-05' AS Date), CAST(N'2023-10-11' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (23, 8, 16, CAST(N'2023-11-03' AS Date), CAST(N'2023-11-09' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (24, 56, 24, CAST(N'2023-12-19' AS Date), CAST(N'2023-12-25' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (25, 24, 8, CAST(N'2023-01-08' AS Date), CAST(N'2023-01-14' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (26, 14, 2, CAST(N'2023-02-28' AS Date), CAST(N'2023-03-05' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (27, 47, 36, CAST(N'2023-03-25' AS Date), CAST(N'2023-03-31' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (28, 20, 19, CAST(N'2023-04-20' AS Date), CAST(N'2023-04-26' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (29, 36, 47, CAST(N'2023-05-15' AS Date), CAST(N'2023-05-21' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (30, 4, 11, CAST(N'2023-06-10' AS Date), CAST(N'2023-06-16' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (31, 30, 39, CAST(N'2023-07-05' AS Date), CAST(N'2023-07-11' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (32, 13, 28, CAST(N'2023-08-02' AS Date), CAST(N'2023-08-08' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (33, 50, 7, CAST(N'2023-09-03' AS Date), CAST(N'2023-09-09' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (34, 18, 35, CAST(N'2023-10-01' AS Date), CAST(N'2023-10-07' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (35, 33, 17, CAST(N'2023-11-01' AS Date), CAST(N'2023-11-07' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (36, 9, 44, CAST(N'2023-12-04' AS Date), CAST(N'2023-12-10' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (37, 57, 1, CAST(N'2023-01-17' AS Date), CAST(N'2023-01-23' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (38, 21, 29, CAST(N'2023-02-12' AS Date), CAST(N'2023-02-18' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (39, 38, 26, CAST(N'2023-03-15' AS Date), CAST(N'2023-03-21' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (40, 22, 14, CAST(N'2023-04-10' AS Date), CAST(N'2023-04-16' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (41, 55, 43, CAST(N'2023-05-08' AS Date), CAST(N'2023-05-14' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (42, 15, 31, CAST(N'2023-06-06' AS Date), CAST(N'2023-06-12' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (43, 41, 45, CAST(N'2023-07-03' AS Date), CAST(N'2023-07-09' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (44, 27, 23, CAST(N'2023-08-01' AS Date), CAST(N'2023-08-07' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (45, 52, 40, CAST(N'2023-09-03' AS Date), CAST(N'2023-09-09' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (46, 26, 4, CAST(N'2023-10-01' AS Date), CAST(N'2023-10-07' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (47, 44, 37, CAST(N'2023-11-07' AS Date), CAST(N'2023-11-13' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (48, 19, 22, CAST(N'2023-12-05' AS Date), CAST(N'2023-12-11' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (49, 58, 9, CAST(N'2023-01-12' AS Date), CAST(N'2023-01-18' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (50, 23, 38, CAST(N'2023-02-07' AS Date), CAST(N'2023-02-13' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (51, 40, 20, CAST(N'2023-03-05' AS Date), CAST(N'2023-03-11' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (52, 25, 33, CAST(N'2023-04-02' AS Date), CAST(N'2023-04-08' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (53, 39, 27, CAST(N'2023-05-01' AS Date), CAST(N'2023-05-07' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (54, 16, 15, CAST(N'2023-06-05' AS Date), CAST(N'2023-06-11' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (55, 32, 10, CAST(N'2023-07-03' AS Date), CAST(N'2023-07-09' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (56, 6, 46, CAST(N'2023-08-07' AS Date), CAST(N'2023-08-13' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (57, 43, 21, CAST(N'2023-09-04' AS Date), CAST(N'2023-09-10' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (58, 5, 16, CAST(N'2023-10-02' AS Date), CAST(N'2023-10-08' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (59, 31, 25, CAST(N'2023-11-06' AS Date), CAST(N'2023-11-12' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (60, 48, 5, CAST(N'2023-12-04' AS Date), CAST(N'2023-12-10' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (61, 11, 8, CAST(N'2023-01-01' AS Date), CAST(N'2023-01-07' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (62, 57, 42, CAST(N'2023-02-05' AS Date), CAST(N'2023-02-11' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (63, 14, 35, CAST(N'2023-03-05' AS Date), CAST(N'2023-03-11' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (64, 47, 12, CAST(N'2023-04-02' AS Date), CAST(N'2023-04-08' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (65, 21, 30, CAST(N'2023-05-01' AS Date), CAST(N'2023-05-07' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (66, 37, 19, CAST(N'2023-06-05' AS Date), CAST(N'2023-06-11' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (67, 8, 36, CAST(N'2023-07-03' AS Date), CAST(N'2023-07-09' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (68, 54, 11, CAST(N'2023-08-07' AS Date), CAST(N'2023-08-13' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (69, 20, 28, CAST(N'2023-09-04' AS Date), CAST(N'2023-09-10' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (70, 36, 47, CAST(N'2023-10-02' AS Date), CAST(N'2023-10-08' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (71, 4, 13, CAST(N'2023-11-06' AS Date), CAST(N'2023-11-12' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (72, 30, 10, CAST(N'2023-12-04' AS Date), CAST(N'2023-12-10' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (73, 13, 20, CAST(N'2023-01-01' AS Date), CAST(N'2023-01-07' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (74, 50, 33, CAST(N'2023-02-05' AS Date), CAST(N'2023-02-11' AS Date))
INSERT [dbo].[Rentals] ([RentalID], [CustomerID], [EquipmentID], [RentalDate], [ReturnDate]) VALUES (75, 18, 26, CAST(N'2023-03-05' AS Date), CAST(N'2023-03-11' AS Date))
GO
ALTER TABLE [dbo].[Customers]  WITH CHECK ADD FOREIGN KEY([AddressID])
REFERENCES [dbo].[Address] ([ID])
GO
ALTER TABLE [dbo].[Employees]  WITH CHECK ADD FOREIGN KEY([AddressID])
REFERENCES [dbo].[Address] ([ID])
GO
ALTER TABLE [dbo].[Equipment_To_Categories]  WITH CHECK ADD FOREIGN KEY([CategoryID])
REFERENCES [dbo].[Categories] ([ID])
GO
ALTER TABLE [dbo].[Equipment_To_Categories]  WITH CHECK ADD FOREIGN KEY([EquipmentID])
REFERENCES [dbo].[Equipment] ([EquipmentID])
GO
ALTER TABLE [dbo].[RentalPayments]  WITH CHECK ADD FOREIGN KEY([RentalID])
REFERENCES [dbo].[Rentals] ([RentalID])
GO
ALTER TABLE [dbo].[Rentals]  WITH CHECK ADD FOREIGN KEY([CustomerID])
REFERENCES [dbo].[Customers] ([CustomerID])
GO
ALTER TABLE [dbo].[Rentals]  WITH CHECK ADD FOREIGN KEY([EquipmentID])
REFERENCES [dbo].[Equipment] ([EquipmentID])
GO
