SONGIFY: APLIKACJA DO ZARZĄDZANIA ALBUMAMI, ARTYSTAMI I PIOSENKAMI

1. można dodać artystę (nazwę artysty)
2. można dodać gatunek muzyczyny (nazwa gatunku)
3. można dodać album (tytuł, datę wydania, ale musi być w nim przynajmniej jedna piosenka)
4. można dodać piosenkę (tytuł, data wydania, ale musi być w nim przynajmniej jedna piosenka)
5. można dodać artystę (usuwamy wtedy jego piosenki oraz albumy, ale jeśli było więcej niż jeden artysta w albumie, to usuwamy tylko artystę)
6. można usunąć gatunek muzyczny (ale nie może istnieć piosenka z takim gatunkiem)
7. można usunąc album (ale dopiero wtedy kiedy nie ma już żadnej piosenki przypisanej do album)
8. można usunąć piosenkę, ale nie usuwamy albumu i artystów gdy była 1 piosenka w albumie
9. można edytować nazwę artysty
10. można edytować nazwę gatunku muzycznego 
11. można edytować album (dodawać piosenki,artystów,zmieniając nazwę albumu)
12. można edytować piosenkę (czas trwania, artystę, nazwę piosenki)
13. można przypisać piosenki tylko do albumów
14. można przypisać piosenki do artysty (poprzez album)
15. można przypisać tylko jeden gatunek muzyczny do piosenki
16. gdy nie ma przypisanego gatunku muzycznego do piosenki, to wyświetlamy "default"
17. można wyświetlać wszystkie piosenki 
18. można wyświetlać wszystkie gatunki
19. można wyświetlać wszystkich artystów 
20. można wyświetlać wszystkie albumy
21. można wyświetlać konkretne albumy z artystami oraz piosenkami w albumie 
22. można wyświetlać konkretne gatunki muzyczne wraz z piosenkami
23. można wyświetlać konkretnych artystów wraz z ich albumami
24. chcemy mieć trwałe dane
25. **25.Security:**
26. Kazdy bez uwierzytelnienia (authentication) moze przegladac piosenki, albumy itp (gosc niezalogowany)
27. Są 2 role: ROLE_USER i ROLE_ADMIN
28. Uzywanie bezstanowego tokena JWT (uzyskuje go po zalogowaniu) - wlasna implementacja authorization i potem oauth google
29. Tylko admin moze przejrzec loginy i role uzytkownikow endpoint /users
30. zeby zostac uzytkownikiem trzeba sie zarejstrowac login/haslo - wlasna implementacja i GOOGLE
31. zapisujemy uzytkownikia i admina do bazy danych (w przypadku wlasnej implementacji)
32. uzytkownik może wyświetlać piosenki, ale nie może zarządzać (w przyszlosc uzytkownik moze miec swoj profil, a tam "ulubione piosenki") - ROLE_USER
33. 

HAPPY PATH (user tworzy album "Eminema" z piosenkami "Til i collapse", "Lose yourself" o gatunku rap)

given there is no songs, artists, albums and genres created before

1. When I go to /songs then I can see no songs
2. when I post to /songs with Song "Til i collapse" then Song "Til i collapse" is returned with id 1 and status OK 
3. when I post to /songs with Song "Lose yourself" then Song "Lose yourself" is returned with id 2 and status OK 
4. when I go to /genre then I can see no genres
5. when I post to /genre with Genre "Rap" then Genre "Rap" is returned with id 1
6. when I go to /song/1 then I can see default genre
7. when I put to /song/1/genre/1 then Genre with id 1 ("Rap") is added to Song with id 1 ("Til i collapse")
8.