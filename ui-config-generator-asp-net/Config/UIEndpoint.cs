namespace UIConfigGenerator.Config
{
    internal class UIEndpoint
    {
        public required string Name { get; set; }
        public required string Mapping { get; set; }
        public required UIRequestType RequestType { get; set; }
        public UIRequestBody? RequestBody { get; set; }
        public List<UIField>? PathParams { get; set; }
        public List<UIField>? QueryParams { get; set; }
        public List<UIField>? ResponseBody { get; set; }
    }
}
