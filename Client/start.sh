
BUILD_DIR="target"
MAIN_CLASS="Main"


cd "$BUILD_DIR" || exit 1
if java "$MAIN_CLASS"; then
    echo -e "${GREEN}✓ Programme exécuté avec succès !${NC}"
else
    echo -e "${RED}✗ Erreur lors de l'exécution du programme${NC}"
    exit 1
fi