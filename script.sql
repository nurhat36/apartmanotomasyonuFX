USE [APARTMAN]
GO
/****** Object:  User [udemy1]    Script Date: 9.12.2024 10:33:44 ******/
CREATE USER [udemy1] FOR LOGIN [udemy1] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  Table [dbo].[aidat_gelirleri_table]    Script Date: 9.12.2024 10:33:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[aidat_gelirleri_table](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[bina_no] [int] NOT NULL,
	[Daire_no] [int] NOT NULL,
	[Tarih] [date] NOT NULL,
	[miktar] [int] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[AidatOdemesi]    Script Date: 9.12.2024 10:33:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AidatOdemesi](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[DaireNo] [int] NULL,
	[Para] [int] NULL,
	[Tarih] [date] NULL,
 CONSTRAINT [PK_AidatOdemesi] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Bina_Giderleri_table]    Script Date: 9.12.2024 10:33:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Bina_Giderleri_table](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[Bina_no] [int] NOT NULL,
	[tarih] [date] NOT NULL,
	[Gidar_Türü] [nvarchar](max) NOT NULL,
	[miktar] [int] NOT NULL,
	[dekont] [varbinary](max) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Gider_Tablosu]    Script Date: 9.12.2024 10:33:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Gider_Tablosu](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Para] [int] NULL,
	[Tarih] [date] NULL,
	[Gider] [nvarchar](50) NULL,
 CONSTRAINT [PK_Gider_Tablosu] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[kullaniciler_table]    Script Date: 9.12.2024 10:33:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[kullaniciler_table](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[bina_no] [int] NOT NULL,
	[daire_no] [int] NULL,
	[şifre] [nvarchar](max) NULL,
	[Telefon_No] [nvarchar](50) NULL,
	[e_posta] [nvarchar](max) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[people]    Script Date: 9.12.2024 10:33:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[people](
	[name] [varchar](max) NULL,
	[age] [bigint] NULL,
	[city] [varchar](max) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[sikayet_table]    Script Date: 9.12.2024 10:33:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[sikayet_table](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[bina_no] [int] NOT NULL,
	[daire_no] [int] NOT NULL,
	[tarih] [date] NOT NULL,
	[sikayet] [nvarchar](max) NOT NULL,
	[cozulme_durumu] [nvarchar](max) NOT NULL,
	[sikayet_resmi] [varbinary](max) NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[yötici_kayitlari_table]    Script Date: 9.12.2024 10:33:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[yötici_kayitlari_table](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Bina_No] [int] NOT NULL,
	[Daire_Sayısı] [int] NULL,
	[şifre] [nvarchar](max) NULL,
	[aidat] [int] NULL,
	[Telefon_No] [nvarchar](max) NULL,
	[e_posta] [nvarchar](max) NULL,
 CONSTRAINT [PK_yötici_kayitlari_table] PRIMARY KEY CLUSTERED 
(
	[Bina_No] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  StoredProcedure [dbo].[giderlerim]    Script Date: 9.12.2024 10:33:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create proc [dbo].[giderlerim]
@Para int,
@Tarih date,
@gider nvarchar(50)

as
begin
insert into Gider_Tablosu(Gider,Para,Tarih) values (@gider,@Para,@Tarih)
end
GO
/****** Object:  StoredProcedure [dbo].[odeme_al]    Script Date: 9.12.2024 10:33:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[odeme_al]
@Daireno int,
@Para int,
@Tarih date

as 
begin
insert into AidatOdemesi(DaireNo,Para,Tarih) values (@Daireno,@Para,@Tarih)
end
GO
