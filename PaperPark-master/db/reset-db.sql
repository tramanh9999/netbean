use PaperPark
go

delete from Model_Tag_Mapping
go

delete from model
go

delete from category
go

delete from tag
go

dbcc checkident ('model', reseed, 0)
go
