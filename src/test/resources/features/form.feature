Feature: Wypełnianie formularza na stronie rrogacz.pl

  Scenario: Wypełnienie danych osoby uprawiającej sport i dodatkowa grającej w piłkę nożną
    Given Otwieram stronę formularza
    When Wpisuję Imię osoby uprawiającej sport "Jan" i Nazwisko "Kowalski"
    And Wpisuję imię dodatkowa grającej w piłkę nożną "Paweł" i nazwisko "Nowak"
    Then Formularz jest wypełniony poprawnie
    And Widzę komunikat sukcesu
