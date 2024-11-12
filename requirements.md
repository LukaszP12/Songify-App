SONGIFY: APLIKACJA DO ZARZĄDZANIA ALBUMAMI, ARTYSTAMI I PIOSENKAMI

1. można dodać artystę (nazwę artysty)
2. można dodać gatunek muzyczyny (nazwa gatunku)
3. można dodać album (tytuł, datę wydania, ale musi być w nim przynajmniej jedna piosenka)
4. można dodać piosenkę (tytuł, data wydania, ale musi być w nim przynajmniej jedna piosenka)
5. można dodać artystę (usuwamy wtedy jego piosenki oraz albumy, ale jeśli było więcej niż jeden artysta w albumie, to usuwamy tylko artystę)
6. można usunąć gatunek muzyczny (ale nie może istnieć piosenka z takim gatunkiem)
7.
8.
9.
10.
11.
12.
13.
14.
15.
16.
17.
18.
19.
20.
21.
22.

HAPPY PATH (user tworzy album "Eminema" z piosenkami "Til i collapse", "Lose yourself" o gatunku rap)

given there is no songs, artists, albums and genres created before

1. When I go to /songs then I can see no songs
2. when I post to /songs with Song "Til i collapse" then Song "Til i collapse" is returned with id 1 and status OK 
3. when I post to /songs with Song "Lose yourself" then Song "Lose yourself" is returned with id 2 and status OK 
4. when I go to /genre then I can see no genres