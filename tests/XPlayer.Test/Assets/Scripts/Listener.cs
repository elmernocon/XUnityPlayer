using JetBrains.Annotations;
using UnityEngine;

public class Listener : MonoBehaviour
{
    #region Fields

    private Logger _logger;

    #endregion
    
    #region Methods

    [UsedImplicitly]
    public void OnMessage(string message)
    {
        if (_logger != null)
            _logger.AddLog(message);
    }

    #endregion

    #region Unity Methods

    private void Start()
    {
        _logger = Logger.Instance;
    }

    #endregion
}