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
Models/Interests.kt aangemaakt, waar alle categorieën te vinden zullen zijn voor de interessen van de kat. Dit bevat verschillende groepen, waar elke van een lijst Strings bevat. { bvb, de FavoriteFood groep heeft `Chicken`, `Beef`,`Pork` en `Veggie`}  
Styling is nog niet gebeurt. Probleem voor later :)

## Week 7 (11/11): 6 uren:
Begonnen aan bottom bar navigatie:
- NavBottomBar.kt aangemaakt als 'host' voor de nav items. Ook is NavBarItem.kt aangemaakt als items voor de bar.  
***Beide worden nog niet gebruikt**, ik werk tijdelijk met de BottomNavigation + BottomNavigationItems van compose.Material en zal later overschakelen*
- Route(name,route,icon) data class aangemaakt als verbindingslaag voor alle navigatie items van de bar. Deze worden in een lijst gestopt en meegegeven aan de bar, die voor elk een link maakt.
- De navbar past de kleur van de actieve link aan naar één naar keuze (uit)

Routes en lege screens aangemaakt
- AccountScreen.kt: Je account aanmaken / aanpassen
- CategoriesScreen.kt : Naar andere profielen kijken die interessen in gelijk hebben met jezelf.

Beginnen aan theme styling : colors.xml omzetten naar light/dark themes
- Kleur thema aangemaakt via de MaterialThemeBuilder tool
- Alle screens / components maken nu gebruik van de MaterialTheme i.p.v. de colors.xml file.

Kleine aanpassing layout van ProfileCard om uitbreidingen makkelijker te maken.

## Week 8 (18/11): 6 uren:
Niet zo productief geweest deze week, erg veel tijd verloren aan het login systeem en daardoor geen motivatie gevonden om verder te werken op andere taken.
Er is weinig documentatie te vinden die up-to-date is met de huidige practices van Google, waardoor het een beetje lastig wordt. Ik heb dus:
- AccountManager : een centrale classe die de credentials kan ophalen + inladen + bijmaken. Check nu nog niet of de login data klopt met de firebase db
- SignUpResult : een sealed class die alle mogelijke states bevat over hoe inschrijven kan verlopen (Cancelled, Failure, Success)
- SignInResult : een sealed class die alle mogelijke states bevat over hoe inloggen kan verlopen (Cancelled, Failure, Success, NoCredentials)
- LoginState : bevat de mogelijke parameters en variabelen die het vm moet onthouden (username, ingetypt paswoord, zo van die dingen)
- LoginAction : een sealed class die allle acties bevat die het viewmodel moet kunnen ondernemen als reactie op de pagina. In plaats van verschillende methodes aan te roepen en door te geven in de constructor van het screen gebruik ik dit eens. (Misschien later ook andere screens naar dit systeem omzetten)
- LoginViewModel : het viewmodel voor de LoginScreen. Bevat één LoginState object en één centrale methode die alle cases van LoginAction kan behandelen.
- LoginScreen : Username + paswoord velden toegevoegd, plus een switch knop op Login/Register te togglen.
# LOGIN WERKT NIET
Ik begrijp niet waarom, t'is heel frustrerend. Ik gebruik LaunchedEffects om te checken indien de username verandert (via het automatisch inloggen van CredentialManager en niet de login knop). Alleen... dit gebeurt niet, en ik weet niet waarom. Om zeker deze week uit te pluizen.
## Week 9 (25/11): ..... uren:
## Week 10 (02/12): ..... uren:
## Week 11 (09/12): ..... uren:
## Week 12 (16/12): ..... uren: