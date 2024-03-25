package it.polimi.ingsw;

public class Corner {
    private boolean canCover;
    private boolean canBeCovered;
    private boolean containsSymbol;
    private boolean containsResource;
    private SymbolType symbol;
    private ResourceType resource;
    private CornerType cornerType;
    private Card linkedCard;


    /**
     * @author Margherita Marino
     * @param cornerType type of corner to create
     * @throws IllegalArgumentException if cornerType is not a valid CornerType
     */
    public Corner(CornerType cornerType, ResourceType resourceType, SymbolType symbolType) {
        setCornerType(cornerType);
        setResource(resourceType);
        setSymbol(symbolType);

    }

    public boolean ContainsSymbol() {
        return containsSymbol;
    }

    public void setContainsSymbol(boolean containsSymbol) {
        this.containsSymbol = containsSymbol;
    }

    public boolean ContainsResource() {
        return containsResource;
    }

    public void setContainsResource(boolean containsResource) {
        this.containsResource = containsResource;
    }

    public SymbolType getSymbol() {
        return symbol;
    }

    public void setSymbol(SymbolType symbol) {
        this.symbol = symbol;
    }

    public ResourceType getResource() {
        return resource;
    }

    public void setResource(ResourceType resourceType) {
        switch(resourceType) {
            case null:
                this.resource = null;
            case ANIMALKINGDOM:
                this.resource = ResourceType.ANIMALKINGDOM;
                break;
            case FUNGIKINGDOM:
                this.resource = ResourceType.FUNGIKINGDOM;
                break;
            case PLANTKINGDOM:
                this.resource = ResourceType.PLANTKINGDOM;
                break;
            case INSECTKINGDOM:
                this.resource = ResourceType.INSECTKINGDOM;
                break;
            default:
                throw new IllegalArgumentException("Invalid resourceType");

        }

        public void setSymbol(SymbolType symbolType) {
            switch(symbolType) {
                case null:
                    this.symbol = null;
                case QUILL:
                    this.symbol = SymbolType.QUILL;
                    break;
                case INKWELL:
                    this.symbol = SymbolType.INKWELL;
                    break;
                case MANUSCRIPT:
                    this.symbol = SymbolType.MANUSCRIPT;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid symbolType");

            }


    public void setCornerType(CornerType cornerType) {
        switch(cornerType) {
            case TOPR:
                this.cornerType = cornerType.TOPR;
                break;
            case TOPL:
                this.cornerType = cornerType.TOPL;
                break;
            case LOWR:
                this.cornerType = cornerType.LOWR;
                break;
            case LOWL:
                this.cornerType = cornerType.LOWL;
                break;
            default:
                throw new IllegalArgumentException("Invalid corner type");

        }
    }



}
