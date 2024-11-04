# OnlyPaws
## Omschrijving
Het plan is om, voor de grap uiteraard, een dating-app te maken voor poezen. Als u Tinder kent, voila. Dat. Alleen zonder chatten.

## Features
De hoofd features zal ik hier opschrijven van meest tot minst belangrijk. De volgorde is niet heer nauwkeurig, maar geeft een gevoel van wat mijn prioriteiten zijn voor dit project.

- Je krijgt één per één een kat profiel te zien. Deze kan je liken (naar rechts) of disliken (naar links). Beide van deze opties verwijderen de huidige kat van de lijst en brengen een nieuwe aan, en zo blijft het gaan.
- Kat profielen krijgen attributes en voorkeuren (tags). Zo kan je bvb instellen dat je graag kip eet, of liever runtsvlees, vaak buiten gaat of misschien eerder een slaapkop bent, lievelingsspeelgoed, etc. 
- Op een aparte pagina kan je zoeken voor katten met zekere tags
- Profiel aanmaak pagina (hier vul je alle tags in die voor jouw toepasselijk zijn en wordt bij het eerst opstarten gebruikt, verder niet)
- Profiel edit pagina (om je tags te veranderen)
- Seeder toevoegen want tja, veel poes profielen bestaan er niet ofzo. Die data zal ik zelf aanmaken via een Rust script en random cat foto's halen via een API ofzo.
- Eventueel kan er verbinding gemaakt worden met een cloud database (firebase??) om via daar data op te halen.
- Op main pagina, dis/liken gebeurt met slepen, niet klikken. Da's puur decoratief maar kan tijd nemen, dus we zien nog wel.
- Eens er voldoende profielen bestaan via de seeder, onthouden welke profielen al een dis/like hebben en die niet meer tonen op jouw feed (json bestand / local db)
- **HEEL OPTIONEEL** : Een 'weight' systeem ontwerpen. Indien je veel oranje katten liked bvb, komen er meer oranje katten te zien op je live feed.
