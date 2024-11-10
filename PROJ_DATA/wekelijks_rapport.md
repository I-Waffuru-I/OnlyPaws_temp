# Wekelijks rapport
Student(e): Mathijs Verbeure
Overzicht activiteiten
## Vakantieweek (28/10): 8 uren
Begin project:
- Aanmaken github/snel ontwerp
- Aanmaken project files
- OnlyPawsApp.kt aangemaakt, navigatie host + route voor de HomeScreen
- HomeScreen.kt aangemaakt,
- ICatRepository.kt + MockCatRepository.kt aangemaakt, met de standard GetCatProfiles() en GetCatProfile(catId :Int) methodes. MockCatRepository geeft 3 test profiles die hardcoded er in staan.
- ProfileCard.kt aangemaakt met een composable ProfileCard(cat : CatProfile, like : ()->Unit, dislike : ()->Unit )
    - de gegeven profiel toont met twee knoppen, de foto, de naam en de beschrijving.  
    - like / dislike worden via twee grote knoppen aangeroepen die heel de lengte van de composable nemen met ogv 20% van de breedte elk.
- MainScreen.kt is de hoofd pagina, hier worden twee profile cards getekend met data van de repository. Enkel één van de twee is zichtbaar, de andere wordt bewaard om laadtijden te vermijden en wisselt telkens met een nieuwe poes. 

## Week 6 (04/11): 7 uren:

Eerste werking van het liken/disliken:
- De huidige poes wordt verwijdert, de oude komt als display en een nieuwe opgehaald vanuit de repo
- Er is geen verschil tussen like/dislike
- MainScreenViewModel onthoud de vorige kat een die wordt gecalled + vernieuwd bij het dis/liken.
MainScreen krijgt terug een CatProfile, geen CatProfileDuo als parameter. De achterste ProfileCard is nu verwijdert aangezien die toch geen nut heeft.
ProfileScreen:
- ProfileScreen.kt aangemaakt.
- ProfileViewModel.kt aangemaakt, met het viewmodel zelf + ProfileViewUiStates
- Route toegevoegd (oorspronkelijk was ik van plan om heel de CatProfile mee te geven als navigatie details om die niet opnieuw te moeten laden, maar dat wordt niet toegestaan)
- In ProfileCard kan je nu op drie knoppen klikken, allemaal onzichtbaar over heel de hoogte van de card. Deze zijn : [Dislike, Details, Like], en doen wat u zou verwachten.
Models/Interests.kt aangemaakt, waar alle categorieën te vinden zullen zijn voor de interessen van de kat. Dit bevat verschillende groepen, waar elke groep  
Styling is nog niet gebeurt. Probleem voor later :)

## Week 7 (11/11): ..... uren:
## Week 8 (18/11): ..... uren:
## Week 9 (25/11): ..... uren:
## Week 10 (02/12): ..... uren:
## Week 11 (09/12): ..... uren:
## Week 12 (16/12): ..... uren: