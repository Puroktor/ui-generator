namespace UIConfigGenerator.Config
{
    internal class UIRequestBody
    {
        public required string EntityName { get; set; }
        public required List<UIField> Fields { get; set; }
    }
}
