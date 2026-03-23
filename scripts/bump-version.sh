#!/bin/bash
#
# ThingLinks Unified Version Bump Script
#
# Usage:
#   ./scripts/bump-version.sh <new-version>
#
# Example:
#   ./scripts/bump-version.sh 1.4.0
#
# This script updates the project version in ALL locations:
#   - Backend: 3 pom.xml files defining <revision> (thinglinks-cloud, thinglinks-job, thinglinks-sdk)
#   - Frontend: 3 package.json files (thinglinks-web, thinglinks-web-visualize, thinglinks-web/tests/server)
#
# NOTE: thinglinks-util.version (base library) is NOT modified by this script.
#       It has its own release cycle and should be updated manually when needed.
#

set -euo pipefail

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Get project root (parent of scripts/)
PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"

# Validate input
if [ $# -ne 1 ]; then
    echo -e "${RED}Error: Please provide the new version number.${NC}"
    echo "Usage: $0 <new-version>"
    echo "Example: $0 1.4.0"
    exit 1
fi

NEW_VERSION="$1"

# Validate version format (semver-like: X.Y.Z or X.Y.Z-SNAPSHOT etc.)
if ! echo "$NEW_VERSION" | grep -qE '^[0-9]+\.[0-9]+\.[0-9]+(-[a-zA-Z0-9.]+)?$'; then
    echo -e "${RED}Error: Invalid version format '${NEW_VERSION}'.${NC}"
    echo "Expected format: X.Y.Z or X.Y.Z-SNAPSHOT"
    exit 1
fi

echo "========================================"
echo " ThingLinks Version Bump"
echo " New version: ${NEW_VERSION}"
echo "========================================"
echo ""

# ─── Backend: pom.xml <revision> updates ───────────────────────────────────

POM_FILES=(
    "thinglinks-cloud/thinglinks-dependencies-parent/pom.xml"
    "thinglinks-cloud/thinglinks-sdk/pom.xml"
    "thinglinks-job/pom.xml"
)

echo -e "${YELLOW}[Backend] Updating <revision> in pom.xml files...${NC}"
for pom in "${POM_FILES[@]}"; do
    FILE="${PROJECT_ROOT}/${pom}"
    if [ -f "$FILE" ]; then
        # Replace <revision>X.Y.Z</revision> with new version
        if grep -q "<revision>" "$FILE"; then
            sed -i.bak "s|<revision>[^<]*</revision>|<revision>${NEW_VERSION}</revision>|g" "$FILE"
            rm -f "${FILE}.bak"
            CURRENT=$(grep -o "<revision>[^<]*</revision>" "$FILE" | head -1)
            echo -e "  ${GREEN}✔${NC} ${pom} → ${CURRENT}"
        else
            echo -e "  ${RED}✕${NC} ${pom} — <revision> not found!"
        fi
    else
        echo -e "  ${RED}✕${NC} ${pom} — file not found!"
    fi
done

# ─── Frontend: package.json version updates ────────────────────────────────

PACKAGE_FILES=(
    "thinglinks-web/package.json"
    "thinglinks-web-visualize/package.json"
    "thinglinks-web/tests/server/package.json"
)

echo ""
echo -e "${YELLOW}[Frontend] Updating \"version\" in package.json files...${NC}"
for pkg in "${PACKAGE_FILES[@]}"; do
    FILE="${PROJECT_ROOT}/${pkg}"
    if [ -f "$FILE" ]; then
        # Replace "version": "X.Y.Z" with new version
        sed -i.bak "s|\"version\": \"[^\"]*\"|\"version\": \"${NEW_VERSION}\"|" "$FILE"
        rm -f "${FILE}.bak"
        CURRENT=$(grep -o '"version": "[^"]*"' "$FILE" | head -1)
        echo -e "  ${GREEN}✔${NC} ${pkg} → ${CURRENT}"
    else
        echo -e "  ${RED}✕${NC} ${pkg} — file not found!"
    fi
done

# ─── Verification ──────────────────────────────────────────────────────────

echo ""
echo -e "${YELLOW}[Verify] Checking all version definitions...${NC}"
echo ""

ERRORS=0

# Check pom.xml revision values
for pom in "${POM_FILES[@]}"; do
    FILE="${PROJECT_ROOT}/${pom}"
    if [ -f "$FILE" ]; then
        ACTUAL=$(grep -o "<revision>[^<]*</revision>" "$FILE" | sed 's/<[^>]*>//g' | head -1)
        if [ "$ACTUAL" = "$NEW_VERSION" ]; then
            echo -e "  ${GREEN}✔${NC} ${pom}: ${ACTUAL}"
        else
            echo -e "  ${RED}✕${NC} ${pom}: expected ${NEW_VERSION}, got ${ACTUAL}"
            ERRORS=$((ERRORS + 1))
        fi
    fi
done

# Check package.json version values
for pkg in "${PACKAGE_FILES[@]}"; do
    FILE="${PROJECT_ROOT}/${pkg}"
    if [ -f "$FILE" ]; then
        ACTUAL=$(grep -o '"version": "[^"]*"' "$FILE" | sed 's/"version": "//;s/"//' | head -1)
        if [ "$ACTUAL" = "$NEW_VERSION" ]; then
            echo -e "  ${GREEN}✔${NC} ${pkg}: ${ACTUAL}"
        else
            echo -e "  ${RED}✕${NC} ${pkg}: expected ${NEW_VERSION}, got ${ACTUAL}"
            ERRORS=$((ERRORS + 1))
        fi
    fi
done

echo ""
if [ $ERRORS -eq 0 ]; then
    echo -e "${GREEN}All 6 version definitions updated to ${NEW_VERSION} successfully!${NC}"
    echo ""
    echo "Note: thinglinks-util.version remains unchanged (separate release cycle)."
    echo ""
    echo "Next steps:"
    echo "  1. Review changes: git diff"
    echo "  2. Build & test:   mvn clean compile -f thinglinks-cloud/pom.xml"
    echo "  3. Commit:         git add -A && git commit -m 'chore: bump version to ${NEW_VERSION}'"
else
    echo -e "${RED}${ERRORS} version(s) failed to update. Please check manually.${NC}"
    exit 1
fi
