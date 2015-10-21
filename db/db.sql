user - done
- username (PK)
- name
- password
- id credit card

credit card - done
- number (PK)
- type
- validity

ticket - done
- id
- username
- origin station
- destiny station
- qr code 
- used/not used
- departure time
- arrival time

station - done
- name (PK)
- line (A, C or both)

neighbors station - done
- name1 (FK station)
- name2 (FK station)

timetable - done
- id
- origin station (FK station)
- destiny station (FK station)
   --- these are neighbors stations
- departure time
- arrival time
- lotation

travels train - done
- timetable id (FK)
- date
- passengers number
