insert into moviesdirectors (directorId, movieId) values ((select d.id from directors d where d.firstname = 'testDirectorFirstname'), (select m.id from movies m where m.name = 'testMovie'))