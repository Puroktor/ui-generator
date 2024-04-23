namespace UIConfigGenerator.Attributes
{
    [AttributeUsage(AttributeTargets.Class | AttributeTargets.Method | AttributeTargets.Enum | AttributeTargets.Parameter | AttributeTargets.Property, AllowMultiple = false)]
    public class DisplayNameAttribute(string name) : Attribute
    {
        public string Name { get; set; } = name ?? throw new ArgumentNullException(nameof(name));
    }
}
