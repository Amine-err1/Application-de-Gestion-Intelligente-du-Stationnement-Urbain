# Application de Gestion Intelligente du Stationnement Urbain

**Filière :** 4ème année IIR  
**Centre :** My Youssef  
**Année universitaire :** 2025/2026  

**Réalisé par :** Amine Erradha  
**Encadré par :** M. Abderrahim LARHLIMI  


---
### Description du projet

Le projet Application de Gestion Intelligente du Stationnement Urbain s’inscrit dans le cadre du module Java Avancé de la 4ᵉ année IIR. Il vise à concevoir et développer une solution logicielle permettant d’optimiser la gestion et la réservation des places de parking en milieu urbain.

Face à la croissance du nombre de véhicules et aux difficultés croissantes de stationnement dans les villes, cette application propose une approche numérique intelligente pour faciliter l’accès aux parkings, réduire le temps de recherche des places et améliorer la fluidité du trafic. Elle permet aux utilisateurs de consulter les parkings disponibles, d’obtenir des informations détaillées (localisation, capacité, tarif horaire) et de réserver une place en toute simplicité.

L’application repose sur une architecture logicielle structurée, développée en Java (JDK 17) avec Maven pour la gestion du projet et des dépendances. Elle intègre une couche DAO assurant la communication avec la base de données, ainsi que des objets métiers représentant les entités principales du système, notamment les parkings et les places de stationnement.

Deux profils d’utilisateurs sont pris en charge :

L’administrateur, qui peut ajouter, modifier et gérer les parkings.

L’utilisateur, qui peut s’authentifier, consulter les parkings et effectuer des réservations.

Ce projet a permis de mettre en pratique les concepts de la programmation orientée objet, de la gestion de base de données avec JDBC, ainsi que la conception UML, tout en respectant des critères non fonctionnels tels que la sécurité, la performance et l’ergonomie de l’interface.

---
## Table des matières

1. Introduction Générale  
2. Contexte du projet  
3. Problématique  
4. Objectifs  
5. Analyse et Conception  
   - Spécification des besoins  
   - Besoins fonctionnels  
   - Besoins non fonctionnels  
6. Conception UML  
7. Environnement Technique  
8. Architecture logicielle  
9. DAO 
10. Conclusion  

---

## Introduction Générale

Ce projet s’inscrit dans le cadre du module **Java Avancé**. Il vise à concevoir et développer une application de gestion intelligente du stationnement urbain permettant d’optimiser la réservation des places de parking.

---

## 1. Contexte du projet

La croissance urbaine entraîne une augmentation du nombre de véhicules, rendant le stationnement difficile dans les zones urbaines.

---

## 2. Problématique

L’absence de solutions numériques efficaces entraîne une perte de temps, une congestion du trafic et une mauvaise gestion des espaces de stationnement.

---

## 3. Objectifs

Les objectifs principaux du projet sont :

- Permettre la réservation de places de parking  
- Gérer la disponibilité en temps réel  
- Offrir une interface simple et intuitive  

---

## 4. Analyse et Conception

### 4.1 Spécification des besoins

#### 4.1.1 Besoins fonctionnels

- Rechercher un parking par prix  
- Consulter les détails d’un parking  
- Réserver une place  

#### 4.1.2 Besoins non fonctionnels

- Sécurité des données  
- Temps de réponse rapide  
- Ergonomie de l’interface  

---

## 5. Conception UML

### 5.1 Diagramme de classes

*(Diagramme UML illustrant les relations entre les entités du système)*

<img width="913" height="416" alt="image" src="https://github.com/user-attachments/assets/448fa705-cd6c-40a2-8e3c-1337fd302824" />


---

## 6. Environnement Technique

### 6.1 Java (JDK 17)

Java 17 est une version LTS (Long Term Support) du langage Java, reconnue pour ses améliorations en termes de performance, stabilité et sécurité. Elle est recommandée pour les environnements de production modernes.

### 6.2 Maven

Maven est un outil open source de gestion de projets Java. Il facilite :
- La gestion des dépendances  
- Le build et le packaging  
- L’exécution des tests  
- Le déploiement  

Il repose sur le fichier `pom.xml` et suit le principe **Convention over Configuration**.

---
### Conclusion
Ce projet de Smart Parking a permis de d´evelopper une application fonctionnelle r´epondant aux
exigences du cahier des charges, notamment l’authentification des utilisateurs, la r´eservation de
places de parking et la gestion des r´eservations. L’architecture adopt´ee et les technologies utilis´ees
ont assur´e une application structur´ee et coh´erente.
Sur le plan personnel, ce projet a contribu´e au renforcement des comp´etences en Java, JDBC,
Maven et en programmation orient´ee objet, tout en d´eveloppant le travail en ´equipe. Certaines
difficult´es techniques, principalement li´ees `a la gestion de la base de donn´ees et des exceptions, ont´et´e surmont´ees grˆace aux tests et `a la recherche.
Enfin, des am´eliorations futures sont envisageables, telles qu’une version web ou mobile, un
renforcement de la s´ecurit´e et l’int´egration de nouvelles fonctionnalit´es pour rendre l’application
plus compl`ete et plus performante.

