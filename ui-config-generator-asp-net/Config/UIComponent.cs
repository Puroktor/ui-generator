namespace UIConfigGenerator.Config
{
    internal class UIComponent
    {
        public required string Name { get; set; }
        public required List<UIEndpoint> Endpoints { get; set; }
    }
}
