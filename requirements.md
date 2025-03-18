SONGIFY: APLIKACJA DO ZARZĄDZANIA ALBUMAMI, ARTYSTAMI I PIOSENKAMI

1. można dodać artystę (nazwa artysty)
2. można dodać gatunek muzyczny (nazwa gatunku)
3. można dodać album (tytuł, data wydania, ale musi być w nim przynajmniej jedna piosenka)
4. można dodać piosenkę (tytuł, czas trwania, data wydania, oraz artystę do którego należy)
5. można usunąć artystę (usuwamy wtedy jego piosenki oraz albumy)
6. można usunąć gatunek muzyczny (ale nie może istnieć piosenka z takim gatunkim)
7. można usunąć album (ale dopiero wtedy kiedy nie ma już żadnej piosenki przypisanej do albumu)
8. można usunąć piosenkę
9. można edytować piosenki artysty, oraz jego nazwę
10. można edytować nazwę gatunku muzycznego
11. można edytować album (dodawać piosenki, artystów, zmieniać nazwę albumu)
12. można edytować piosenkę (czas trwania, artystę, nazwę piosenki)




HAPPY PATH (user tworzy album "Eminema" z piosenkami "Til I collapse", "Lose Yourself", o gatunku rap)

given there are 2 songs, artists, albums and genres created before

1. when I go to /songs then I can see no songs
2. when I post to /songs with Song "Till I collapse" then Song "Till I collapse" is returned with id 1
3. when I post to /songs with Song "Lose Yourself" then Song "Lose Yourself" is returned with id 2
4. when I go to /genre then I can see no genres