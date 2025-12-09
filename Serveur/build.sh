#!/bin/bash
# clean-and-compile.sh
# Nettoye le répertoire build et compile les fichiers Java

# Couleurs pour les messages
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== Script de nettoyage et compilation Java ===${NC}"

# Vérifier si javac est installé
if ! command -v javac &> /dev/null; then
    echo -e "${RED}Erreur: javac n'est pas installé ou n'est pas dans le PATH${NC}"
    exit 1
fi

# Répertoire de build
BUILD_DIR="target"
SRC_DIR="src"
MAIN_CLASS="Main"
IMAGE_DIR="images"

# 1. Nettoyage du répertoire build
echo -e "${YELLOW}1. Nettoyage du répertoire '$BUILD_DIR'...${NC}"

if [ -d "$BUILD_DIR" ]; then
    rm -rf "$BUILD_DIR"/*
    echo -e "${GREEN}✓ Répertoire '$BUILD_DIR' vidé${NC}"
else
    mkdir -p "$BUILD_DIR"
    echo -e "${GREEN}✓ Répertoire '$BUILD_DIR' créé${NC}"
fi

# 2. Vérification de l'existence des sources
echo -e "${YELLOW}2. Recherche des fichiers Java...${NC}"

if [ ! -d "$SRC_DIR" ]; then
    echo -e "${RED}Erreur: Le répertoire '$SRC_DIR' n'existe pas${NC}"
    echo "Création de la structure de base..."
    mkdir -p "$SRC_DIR"
    echo "// Exemple de fichier Java" > "$SRC_DIR/Main.java"
    echo "public class Main {" >> "$SRC_DIR/Main.java"
    echo "    public static void main(String[] args) {" >> "$SRC_DIR/Main.java"
    echo "        System.out.println(\"Hello, World!\");" >> "$SRC_DIR/Main.java"
    echo "    }" >> "$SRC_DIR/Main.java"
    echo "}" >> "$SRC_DIR/Main.java"
    echo -e "${GREEN}✓ Structure créée avec un exemple Main.java${NC}"
fi

# Compter les fichiers Java
JAVA_FILES=$(find "$SRC_DIR" -name "*.java" 2>/dev/null | wc -l)

if [ "$JAVA_FILES" -eq 0 ]; then
    echo -e "${RED}Erreur: Aucun fichier .java trouvé dans '$SRC_DIR'${NC}"
    exit 1
fi

echo -e "${GREEN}✓ $JAVA_FILES fichier(s) Java trouvé(s)${NC}"

# 3. Compilation
echo -e "${YELLOW}3. Compilation des fichiers Java...${NC}"

# Trouver tous les fichiers Java
FILES_TO_COMPILE=$(find "$SRC_DIR" -name "*.java")

# Options de compilation
JAVAC_OPTS="-d $BUILD_DIR -Xlint:unchecked"

if javac $JAVAC_OPTS $FILES_TO_COMPILE 2>&1; then
    echo -e "${GREEN}✓ Compilation réussie !${NC}"
    
    # Afficher les classes compilées
    echo -e "${YELLOW}Classes compilées dans '$BUILD_DIR':${NC}"
    find "$BUILD_DIR" -name "*.class" | while read class; do
        echo "  • $(basename "$class")"
    done
else
    echo -e "${RED}✗ Erreur de compilation${NC}"
    exit 1
fi

if cp -r "$IMAGE_DIR" "$BUILD_DIR/"; then
    echo -e "${GREEN}✓ Répertoire d'images copié dans '$BUILD_DIR'${NC}"
else
    echo -e "${RED}✗ Erreur lors de la copie du répertoire d'images${NC}"
    exit 1
fi

echo -e "${BLUE}=== Terminé avec succès ===${NC}"

echo -e "${YELLOW}Excecution du programme:${NC}"

cd "$BUILD_DIR" || exit 1
if java "$MAIN_CLASS"; then
    echo -e "${GREEN}✓ Programme exécuté avec succès !${NC}"
else
    echo -e "${RED}✗ Erreur lors de l'exécution du programme${NC}"
    exit 1
fi