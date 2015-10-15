user
- username (PK)
- name
- password
- id credit card

credit card
- number (PK)
- type
- validity

ticket
- id
- username
- origin station
- destiny station
- qr code 
- used/not used
- departure time
- arrival time

station
- name (PK)
- line (A, C or both)

neighbors station
- name1 (FK station)
- name2 (FK station)

timetable
- id
- origin station (FK station)
- destiny station (FK station)
   --- these are neighbors stations
- departure time
- arrival time
- lotation

travels train
- timetable id (FK)
- date
- passengers number
