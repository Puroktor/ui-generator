namespace UIConfigGenerator.Config
{
    internal class UITextField : UIField
    {
        public int? MinLength { get; set; }
        public int? MaxLength { get; set; }
        public string? Pattern { get; set; }
    }
}
