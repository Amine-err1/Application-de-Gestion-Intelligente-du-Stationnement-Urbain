# Application de Gestion Intelligente du Stationnement Urbain

**Filière :** 4ème année IIR  
**Centre :** My Youssef  
**Année universitaire :** 2025/2026  

**Réalisé par :** Amine Erradha  
**Encadré par :** M. Abderrahim LARHLIMI  

---

## Remerciements

Nous tenons à remercier notre encadrant pédagogique **M. Abderrahim LARHLIMI** pour son accompagnement, ainsi que l’administration de l’EMSI pour les moyens mis à disposition ayant contribué à la réussite de ce projet.

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
10. Interfaces  
11. Conclusion  

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

## 7. Architecture logicielle

### 7.1 Les objets métiers

```java
public class Parking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Column(name = "capacite", nullable = false)
    private Integer capacite;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "tarif_horaire")
    private Double tarifHoraire;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Place> places = new HashSet<>();
}
