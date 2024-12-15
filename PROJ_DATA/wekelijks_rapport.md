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


## Week 9 (25/11): 8-9 uren:
Ik heb dus uitgepluisd waarom die LaunchedEffects niets deden : *ik veranderde de foute waarde in het viewmodel*. Leuk.
Heel deze week ben ik bezig geweest met het login systeem. De struktuur en UX is aangepast. Bij het eerst openen van de app krijg je de LoginScreen te zien, zoals altijd, alleen er zullen geen credentials bestaan die de CredManager kan tonen (behalve als je al de app op een ander toestel hebt gebruikt). Je sluit deze af en kan op Register klikken, die naar de RegisterScreen brengt.
Login :
    - Users worden in Firebase gezet. Indien die niet bestaat wordt een Error gegeven.
    - Inloggen gebeurt met een email (maakt niet uit of die wel bestaat) en een paswoord (lengte > 6 chars)
    - LoginScreen bevat geen invoer velden meer. De Credential Manager zorgt voor het onthouden van gegevens doorheen je toestellen met hetzelfde Google account.
Register :
    - Eigelijk is de structuur qwa classes bijna identiek, alleen met Register i.p.v. Login.
    - Een knop opent een PhotoPicker instantie waarmee je een afbeelding van je toestel kunt inladen. De URI wordt in een text-vak geschreven, **MAAR**, deze hoor je niet te gebruiken (voor nu) 
    - Een andere knop staat er onder. Hiermee gaat een random kat foto gevonden worden die wel online te vinden is. Dit moet je eigenlijk gebruiken voor je profiel te creeëren.
    - Verder wordt een gebruikersnaam (de naam van de poes), een email en een paswoord verwacht. 

## Week 10 (02/12): 9 uren:

Database : 
- Structuur wat uitgebreid. Er is nu een `users`, `cats` en `user_ids` deel in Firebase. 
- `users` bevat login gegevens en een referentie naar het `cat` gedeelte. Dit wordt opgevraagd bij de login pagina, *enkel en alleen jouw eigen informatie uiteraard*
- `cats` bevat de informatie die men te zien krijgt bij swipen. Naam, beschrijving, afbeelding.
- Bij het registreren wordt het ID als key gebruikt in de `users` map, maar wordt die ook in het array `user_ids` toegevoegd.
- Aangezien er maar één profiel te vinden is per account kan ik loopen over die lijst, tellen en er van uitgaan dat de teller +1 uniek is. Dit wordt gebruikt bij het opslaan van de gebruiker.
- Dummy data aangemaakt voor wat gebruikers.
-  **Ik krijg nu zo'n leuke bug waarbij een firebase snapshot in de cat repo te nemen heel een blok code lijkt over de steken zonder iets te doen. Heel gek. Om deze week te fixen**

MainScreen :
- Maakt nu gebruik van dat state systeem van Login/Register; MainState (alle info voor de screen), MainStateList(een enum) en MainAction zijn aangemaakt en toegevoegd.
- De code van ProfileCard heb ik... idk waarom, uit de card en rechstreeks in de screengezet. Kzal dat later wel terug omzetten, moest een soort brainfart zijn geweest.
- MockCat/UserRepo zijn alletwee omgezet naar Firebase repos en halen data online op.

Login :
- Volledig functioneel, data wordt tijdig opgehaald.

Register :
- Moet nog wat testen of firebase opslaan lukt, maar de structuur is volledig af buiten dit.

## Week 11 (09/12): 9 uren:
Database :
- Dat probleem met de CatProfiles ophalen is opgelost. Ik was vergeten een default (lege) constructor te maken voor het CatProfile model.
- UserProfile + opgeslagen data is nu vervormdt naar een kleiner object om te vermijden informatie dubbel op te slaan. Zo wordt in `users` alleen nog het CatID, LastSeenCatID en Email onthouden, terwijl `cats` alle echte informatie bevat.

Account :
- AccountScreen werkt nu met het zelfde State systeem als de andere screens.
- ValueUpdateField aangemaakt, een composable om velden aan te passen in X of Y context (zal toegepast worden in de AccountScreen bvb). Is wel totaal niet af en ziet er als just niks uit.

Register :
- Checks toegevoegd om te bepalen of je zelfs mag inloggen. Indien niet wordt de `Register` knop uitgeschakeld
- Registreren wordt opgesplitst in twee delen. Eerst kom je op RegisterScreen, voeg je een email en wachtwoord in, en pas ééns die beide voldoen aan de checks kan je door naar :
- de RegisterDetails screen. Hier vul je een gebruikersnaam, beschrijving en afbeelding in met wat invoervelden + die Random Cat knop.
- Checks : Paswoord langer dan 6 chars (wordt altijd gecheckt) en email die nog niet in gebruik is (enkel wanneer je Register klikt wordt dit gecheckt) 

## Week 12 (16/12): ..... uren: