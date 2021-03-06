USE [PaperPark]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 7/8/2019 16:46:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[Id] [varchar](50) NOT NULL,
	[Name] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Model]    Script Date: 7/8/2019 16:46:02 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Model](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](100) NULL,
	[NumOfSheets] [int] NULL,
	[NumOfParts] [int] NULL,
	[Difficulty] [int] NULL,
	[Format] [varchar](10) NULL,
	[ImageSrc] [nvarchar](500) NULL,
	[Link] [nvarchar](500) NULL,
	[HasInstruction] [bit] NULL,
	[CategoryId] [varchar](50) NULL,
 CONSTRAINT [PK__Model__3214EC07FB58E912] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Model_Tag_Mapping]    Script Date: 7/8/2019 16:46:02 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Model_Tag_Mapping](
	[ModelId] [int] NOT NULL,
	[TagId] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ModelId] ASC,
	[TagId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Tag]    Script Date: 7/8/2019 16:46:02 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Tag](
	[Id] [varchar](50) NOT NULL,
	[Name] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Model] ADD  CONSTRAINT [DF__Model__NumOfShee__4D94879B]  DEFAULT ((0)) FOR [NumOfSheets]
GO
ALTER TABLE [dbo].[Model] ADD  CONSTRAINT [DF__Model__Difficult__4E88ABD4]  DEFAULT ((3)) FOR [Difficulty]
GO
ALTER TABLE [dbo].[Model] ADD  CONSTRAINT [DF__Model__HasInstru__4F7CD00D]  DEFAULT ((0)) FOR [HasInstruction]
GO
ALTER TABLE [dbo].[Model]  WITH CHECK ADD  CONSTRAINT [FK__Model__CategoryI__5070F446] FOREIGN KEY([CategoryId])
REFERENCES [dbo].[Category] ([Id])
GO
ALTER TABLE [dbo].[Model] CHECK CONSTRAINT [FK__Model__CategoryI__5070F446]
GO
ALTER TABLE [dbo].[Model_Tag_Mapping]  WITH CHECK ADD  CONSTRAINT [FK__Model_Tag__Model__534D60F1] FOREIGN KEY([ModelId])
REFERENCES [dbo].[Model] ([Id])
GO
ALTER TABLE [dbo].[Model_Tag_Mapping] CHECK CONSTRAINT [FK__Model_Tag__Model__534D60F1]
GO
ALTER TABLE [dbo].[Model_Tag_Mapping]  WITH CHECK ADD FOREIGN KEY([TagId])
REFERENCES [dbo].[Tag] ([Id])
GO
