user - done - model & controller created
- username (PK)
- name
- password
- id credit card

credit card - done - model & controller created
- number (PK)
- type
- validity

ticket - done - model & controller created
- id
- username
- origin station
- destiny station
- qr code 
- used/not used
- departure time
- arrival time

station - done - model & controller created
- name (PK)
- line (A, C or both)

neighbors station - done - model created
- name1 (FK station)
- name2 (FK station)

timetable - done - model & controller created
- id
- origin station (FK station)
- destiny station (FK station)
   --- these are neighbors stations
- departure time
- arrival time
- lotation

travels train - done - model & controller created
- timetable id (FK)
- date
- passengers number
