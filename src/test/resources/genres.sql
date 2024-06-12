insert into genres (id, name)
values (((select max(g2.id) from genres g2) + 1), 'testGenre');