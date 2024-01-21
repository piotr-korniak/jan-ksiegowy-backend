package pl.janksiegowy.backend.item;

public enum ItemType {
    A { // Asset
        @Override public <T> T accept( ItemTypeVisitor<T> visitor) {
            return visitor.visitAsset();
        }
    },
    S { // Service
        @Override public <T> T accept( ItemTypeVisitor<T> visitor) {
            return visitor.visitService();
        }
    },
    M { // Material
        @Override public <T> T accept( ItemTypeVisitor<T> visitor) {
            return visitor.visitMaterial();
        }
    },
    P { // Product
        @Override public <T> T accept( ItemTypeVisitor<T> visitor) {
            return visitor.visitProduct();
        }
    };

    public abstract <T> T accept( ItemTypeVisitor<T> visitor);

    interface ItemTypeVisitor<T> {
        T visitAsset();
        T visitService();
        T visitMaterial();
        T visitProduct();

    }
}
